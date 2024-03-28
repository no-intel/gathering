package org.noint.gathering.domain.gathering.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.dto.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.dto.response.GatheringInfoResDto;
import org.noint.gathering.domain.gathering.repository.GatheringRepository;
import org.noint.gathering.domain.member.service.MemberQueryService;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatheringCommendService {

    private final GatheringRepository gatheringRepository;

    private final MemberQueryService memberQueryService;

    public GatheringInfoResDto createGathering(Long memberId, GatheringReqDto request) {
        Member member = memberQueryService.getMember(memberId);
        Gathering gathering = new Gathering(request.subject(), request.description(), request.maxMembers(), member);
        gatheringRepository.save(gathering);
        return new GatheringInfoResDto(gathering.getSubject(), gathering.getDescription(), gathering.getMaxMembers(), member.getName(), member.getId());
    }
}
