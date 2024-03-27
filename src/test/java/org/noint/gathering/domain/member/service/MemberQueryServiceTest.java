package org.noint.gathering.domain.member.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.member.dto.request.LoginReqDto;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.dto.response.MyInfoResDto;
import org.noint.gathering.domain.member.exception.MemberException;
import org.noint.gathering.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberQueryServiceTest {

    @Autowired
    MemberCommendService memberCommendService;

    @Autowired
    MemberQueryService memberQueryService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberCommendService.register(new RegisterReqDto("test1@b.c", "test1", "password1"));
    }

    @Test
    void 로그인_성공() throws Exception {
        //given
        LoginReqDto request = new LoginReqDto("test1@b.c", "password1");

        //when
        MyInfoResDto myInfo = memberQueryService.login(request);

        //then
        assertThat(myInfo.email()).isEqualTo(request.email());
    }

    @Test
    void 로그인_실패_이메일_불일치() throws Exception {
        //given
        LoginReqDto request = new LoginReqDto("test11@b.c", "password1");

        //when
        ThrowingCallable throwable = () -> memberQueryService.login(request);

        //then
        assertThatThrownBy(throwable).isInstanceOf(MemberException.class);
    }

    @Test
    void 로그인_실패_비밀번호_불일치() throws Exception {
        //given
        LoginReqDto request = new LoginReqDto("test1@b.c", "password11");

        //when
        ThrowingCallable throwable = () -> memberQueryService.login(request);

        //then
        assertThatThrownBy(throwable).isInstanceOf(MemberException.class);
    }

}