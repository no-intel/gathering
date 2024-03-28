package org.noint.gathering.domain.gathering.service.gathering;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.noint.gathering.domain.gathering.dto.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.exception.GatheringException;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.service.MemberCommendService;
import org.noint.gathering.entity.Gathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class GatheringQueryServiceTest {
    @Autowired
    GatheringCommendService gatheringCommendService;

    @Autowired
    GatheringQueryService gatheringQueryService;

    @Autowired
    MemberCommendService memberCommendService;

    @BeforeAll
    void beforeAll() {
        memberCommendService.register(new RegisterReqDto("test1@b.c", "test1", "password1"));
        gatheringCommendService.createGathering(1L, new GatheringReqDto("모임 주제1", "설명1", 4));
    }

    @Test
    void 모임_조회_성공() throws Exception {
        //given
        Long gatheringId = 1L;

        //when
        Gathering gathering = gatheringQueryService.getGathering(gatheringId);

        //then
        assertThat(gathering.getId()).isEqualTo(gatheringId);
    }

    @Test
    void 모임_조회_실패() throws Exception {
        //given
        Long gatheringId = 0L;

        //when
        ThrowingCallable throwable = () -> gatheringQueryService.getGathering(gatheringId);

        //then
        assertThatThrownBy(throwable).isInstanceOf(GatheringException.class);
    }
}