package org.noint.gathering.domain.gathering.service.gathering;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.dto.gathering.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.dto.gathering.response.GatheringInfoResDto;
import org.noint.gathering.domain.gathering.repository.gathering.GatheringRepository;
import org.noint.gathering.domain.gathering.service.participant.ParticipantCommendService;
import org.noint.gathering.domain.member.service.MemberQueryService;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatheringCommendService {

    private final GatheringRepository gatheringRepository;

    private final GatheringQueryService gatheringQueryService;

    private final MemberQueryService memberQueryService;

    private final ParticipantCommendService participantCommendService;

    public GatheringInfoResDto createGathering(Long memberId, GatheringReqDto request) {
        Member member = memberQueryService.getMember(memberId);
        Gathering gathering = new Gathering(request.subject(), request.description(), request.maxMembers(), member);
        gatheringRepository.save(gathering);
        return new GatheringInfoResDto(gathering.getSubject(), gathering.getDescription(), gathering.getMaxMembers(), member.getName(), member.getId());
    }

    public void entryGathering(Long memberId, Long gatheringId) {
        Member member = memberQueryService.getMember(memberId);
        Gathering gathering = gatheringQueryService.getGathering(gatheringId);
        participantCommendService.register(member, gathering);
        gathering.entryGathering();
    }
}
