package org.noint.gathering.domain.gathering.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.noint.gathering.domain.gathering.dto.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.dto.response.GatheringInfoResDto;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.service.MemberCommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Transactional
class GatheringCommendServiceTest {

    @Autowired
    GatheringCommendService gatheringCommendService;

    @Autowired
    MemberCommendService memberCommendService;

    @BeforeAll
    void beforeEach() {
        memberCommendService.register(new RegisterReqDto("test1@b.c", "test1", "password1"));
    }

    @Test
    void 모임_개설_성공() throws Exception {
        //given
        Long memberId = 1L;
        GatheringReqDto request = new GatheringReqDto("모임 주제", "모임 설명", 4);

        //when
        GatheringInfoResDto gatheringInfo = gatheringCommendService.createGathering(memberId, request);

        //then
        assertThat(gatheringInfo.creatorId()).isEqualTo(memberId);
        assertThat(gatheringInfo.subject()).isEqualTo(request.subject());
        assertThat(gatheringInfo.maxMembers()).isEqualTo(request.maxMembers());
    }
}