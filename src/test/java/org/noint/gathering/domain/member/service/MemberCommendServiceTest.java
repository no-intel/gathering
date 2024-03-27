package org.noint.gathering.domain.member.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.exception.MemberException;
import org.noint.gathering.domain.member.repository.MemberRepository;
import org.noint.gathering.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberCommendServiceTest {

    @Autowired
    MemberCommendService memberCommendService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository.save(new Member("test1@b.c", "test1", "password1"));
    }

    @Test
    void 회원가입_성공() throws Exception {
        //given
        RegisterReqDto request = new RegisterReqDto("a@b.c", "test2", "password1");

        //when
        Long newMemberId = memberCommendService.regist(request);
        Member findMember = memberRepository.findById(newMemberId).get();

        //then
        assertThat(findMember.getName()).isEqualTo(request.name());
    }

    @Test
    void 회원가입_이름_중복_실패() throws Exception {
        //given
        RegisterReqDto request = new RegisterReqDto("test2@b.c", "test1", "password1");

        //when
        ThrowingCallable throwable = () -> memberCommendService.regist(request);

        //then
        assertThatThrownBy(throwable).isInstanceOf(MemberException.class);
    }

    @Test
    void 회원가입_이메일_중복_실패() throws Exception {
        //given
        RegisterReqDto request = new RegisterReqDto("test1@b.c", "test2", "password1");

        //when
        ThrowingCallable throwable = () -> memberCommendService.regist(request);

        //then
        assertThatThrownBy(throwable).isInstanceOf(MemberException.class);
    }
}