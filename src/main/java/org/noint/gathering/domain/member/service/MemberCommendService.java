package org.noint.gathering.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.noint.gathering.domain.member.dto.request.MemberJoinReqDto;
import org.noint.gathering.domain.member.repository.MemberRepository;
import org.noint.gathering.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCommendService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Long join(MemberJoinReqDto request) {
        Member newMember = new Member(request.email(), request.name(), passwordEncoder.encode(request.password()));
        checkDuplicate(request.email(), request.name());
        memberRepository.save(newMember);
        return newMember.getId();
    }

    private void checkDuplicate(String email, String name) {
        if (isExistEmail(email)) {
            throw new RuntimeException("bbbb");
        }
        if (isExistName(name)) {
            throw new RuntimeException("aaa");
        }
    }

    private boolean isExistEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    private boolean isExistName(String name) {
        return memberRepository.findByName(name).isPresent();
    }
}
