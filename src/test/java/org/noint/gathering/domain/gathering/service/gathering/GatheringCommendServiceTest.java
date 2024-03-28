package org.noint.gathering.domain.gathering.service.gathering;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.gathering.dto.gathering.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.dto.gathering.response.GatheringInfoResDto;
import org.noint.gathering.domain.gathering.exception.gathering.GatheringException;
import org.noint.gathering.domain.gathering.repository.participant.ParticipantRepository;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.service.MemberCommendService;
import org.noint.gathering.domain.member.service.MemberQueryService;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.noint.gathering.entity.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@Transactional
class GatheringCommendServiceTest {

    @Autowired GatheringCommendService gatheringCommendService;

    @Autowired MemberCommendService memberCommendService;

    @Autowired GatheringQueryService gatheringQueryService;

    @Autowired MemberQueryService memberQueryService;

    @Autowired ParticipantRepository participantRepository;

    @BeforeEach
    void beforeEach() {
        memberCommendService.register(new RegisterReqDto("test1@b.c", "test1", "password1"));
        gatheringCommendService.createGathering(1L, new GatheringReqDto("모임 주제1", "설명1", 4));
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

    @Test
    void 모임_참가_성공() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;

        //when
        gatheringCommendService.entryGathering(memberId, gatheringId);
        Member member = memberQueryService.getMember(memberId);
        Gathering gathering = gatheringQueryService.getGathering(gatheringId);
        Participant participant = participantRepository.findById(1L).get();

        //then
        assertThat(gathering.getCurrentMembers()).isEqualTo(2);
        assertThat(participant.getMember()).isEqualTo(member);
        assertThat(participant.getGathering()).isEqualTo(gathering);

    }

    @Test
    void 모임_참가_인원_초과_실패() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 2L;

        //when
        Long member2Id = memberCommendService.register(new RegisterReqDto("test2@b.c", "test2", "password1"));
        Long member3Id = memberCommendService.register(new RegisterReqDto("test3@b.c", "test3", "password1"));
        gatheringCommendService.createGathering(member2Id, new GatheringReqDto("모임 주제2", "설명2", 2));
        gatheringCommendService.entryGathering(member3Id, gatheringId);
        Gathering gathering = gatheringQueryService.getGathering(gatheringId);

        ThrowingCallable throwable = () -> gatheringCommendService.entryGathering(memberId, gatheringId);

        //then
        assertThatCode(throwable).isInstanceOf(GatheringException.class);
        assertThat(gathering.getCurrentMembers()).isEqualTo(2);
    }
}