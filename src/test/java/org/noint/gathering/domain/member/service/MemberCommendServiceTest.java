package org.noint.gathering.domain.member.service;

import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.member.dto.request.MemberJoinReqDto;
import org.noint.gathering.domain.member.repository.MemberRepository;
import org.noint.gathering.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberCommendServiceTest {

    @Autowired
    MemberCommendService memberCommendService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        MemberJoinReqDto request = new MemberJoinReqDto("a@b.c", "test1", "password1");

        //when
        Long newMemberId = memberCommendService.join(request);
        Member findMember = memberRepository.findById(newMemberId).get();

        //then
        assertThat(findMember.getName()).isEqualTo(request.name());
    }
}