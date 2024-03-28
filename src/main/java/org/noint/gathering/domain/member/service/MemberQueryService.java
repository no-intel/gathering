package org.noint.gathering.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MyInfoResDto login(LoginReqDto request) {
        Optional<Member> memberOptional = memberRepository.findByEmail(request.email());
        if (memberOptional.isEmpty()) {
            log.warn("존재하지 않는 이메일");
            throw new MemberException(LOGIN_FAILED);
        }

        Member findMember = memberOptional.get();
        if (!findMember.isActiveMember()) {
            log.warn("활성중인 계정이 아닙니다.");
            throw new MemberException(LOGIN_FAILED);
        }

        boolean isMatchedPassword = passwordEncoder.matches(request.password(), findMember.getPassword());
        if (!isMatchedPassword) {
            log.warn("비밀번호 불일치");
            throw new MemberException(LOGIN_FAILED);
        }
        return new MyInfoResDto(findMember.getId(), findMember.getEmail(), findMember.getName(), findMember.getMoney());
    }

    public MyInfoResDto myInfo(Long memberId) {
        Member findMember = getMember(memberId);
        return new MyInfoResDto(findMember.getId(), findMember.getEmail(), findMember.getName(), findMember.getMoney());
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
    }
}
