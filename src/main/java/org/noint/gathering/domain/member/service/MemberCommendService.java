package org.noint.gathering.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.dto.request.LoginReqDto;
import org.noint.gathering.domain.member.dto.response.MyInfoResDto;
import org.noint.gathering.domain.member.exception.MemberException;
import org.noint.gathering.domain.member.repository.MemberRepository;
import org.noint.gathering.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.noint.gathering.domain.member.enums.MemberExceptionBody.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommendService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Long register(RegisterReqDto request) {
        Member newMember = new Member(request.email(), request.name(), passwordEncoder.encode(request.password()));
        checkDuplicate(request.email(), request.name());
        memberRepository.save(newMember);
        return newMember.getId();
    }

    private void checkDuplicate(String email, String name) {
        if (isExistEmail(email)) {
            throw new MemberException(EMAIL_DUPLICATE);
        }
        if (isExistName(name)) {
            throw new MemberException(NAME_DUPLICATE);
        }
    }

    private boolean isExistEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    private boolean isExistName(String name) {
        return memberRepository.findByName(name).isPresent();
    }
}
