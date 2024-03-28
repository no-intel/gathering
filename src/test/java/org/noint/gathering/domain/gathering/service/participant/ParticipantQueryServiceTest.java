package org.noint.gathering.domain.gathering.service.participant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.gathering.dto.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.dto.response.ParticipantsResDto;
import org.noint.gathering.domain.gathering.service.gathering.GatheringCommendService;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.service.MemberCommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ParticipantQueryServiceTest {

    @Autowired
    ParticipantQueryService participantQueryService;

    @Autowired
    MemberCommendService memberCommendService;

    @Autowired
    GatheringCommendService gatheringCommendService;

    @BeforeEach
    void beforeEach() {
        System.out.println("=================Before==================");
        for (int i = 0; i < 10; i++) {
            memberCommendService.register(new RegisterReqDto(i+"test@b.c", "test"+i, "password1"));
        }
        gatheringCommendService.createGathering(1L, new GatheringReqDto("모임 주제1", "설명1", 10));
        gatheringCommendService.entryGathering(2L, 1L);
        gatheringCommendService.entryGathering(3L, 1L);
        gatheringCommendService.entryGathering(4L, 1L);
        gatheringCommendService.entryGathering(5L, 1L);
        System.out.println("=========================================");
    }

    @Test
    void 모임_참가자_리스트_조회() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(0, 3);
        Long gatheringId = 1L;

        //when
        Page<ParticipantsResDto> participants = participantQueryService.getParticipants(gatheringId, pageRequest);

        //then
        System.out.println("participants = " + participants);
    }
}