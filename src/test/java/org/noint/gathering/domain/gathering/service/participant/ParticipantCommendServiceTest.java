package org.noint.gathering.domain.gathering.service.participant;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.gathering.dto.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.exception.GatheringException;
import org.noint.gathering.domain.gathering.repository.participant.ParticipantRepository;
import org.noint.gathering.domain.gathering.service.gathering.GatheringCommendService;
import org.noint.gathering.domain.gathering.service.gathering.GatheringQueryService;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.service.MemberCommendService;
import org.noint.gathering.domain.member.service.MemberQueryService;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.noint.gathering.entity.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ParticipantCommendServiceTest {

    @Autowired GatheringCommendService gatheringCommendService;

    @Autowired GatheringQueryService gatheringQueryService;

    @Autowired MemberCommendService memberCommendService;

    @Autowired MemberQueryService memberQueryService;

    @Autowired ParticipantCommendService participantCommendService;

    @Autowired ParticipantRepository participantRepository;

    @BeforeEach
    void beforeEach() {
        memberCommendService.register(new RegisterReqDto("test1@b.c", "test1", "password1"));
        gatheringCommendService.createGathering(1L, new GatheringReqDto("모임 주제1", "설명1", 4));
    }

    @Test
    void 모임_참가_성공() throws Exception {
        //given
        Member member = memberQueryService.getMember(1L);
        Gathering gathering = gatheringQueryService.getGathering(1L);

        //when
        participantCommendService.register(member, gathering);
        List<Participant> participants = participantRepository.findAll();

        //then
        assertThat(participants.size()).isEqualTo(1);
        assertThat(participants.getFirst().getMember()).isEqualTo(member);
        assertThat(participants.getFirst().getGathering()).isEqualTo(gathering);
    }

    @Test
    void 모임_참가_중복_실패() throws Exception {
        //given
        Member member = memberQueryService.getMember(1L);
        Gathering gathering = gatheringQueryService.getGathering(1L);

        //when
        ThrowingCallable throwable = () -> participantCommendService.register(member, gathering);

        //then
        assertThatThrownBy(throwable).isInstanceOf(GatheringException.class);
    }
}