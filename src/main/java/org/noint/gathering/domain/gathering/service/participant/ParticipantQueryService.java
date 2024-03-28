package org.noint.gathering.domain.gathering.service.participant;

import lombok.RequiredArgsConstructor;
import org.noint.gathering.domain.gathering.dto.response.ParticipantsResDto;
import org.noint.gathering.domain.gathering.repository.participant.ParticipantQueryRepository;
import org.noint.gathering.domain.gathering.repository.participant.ParticipantRepository;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParticipantQueryService {

    private final ParticipantRepository participantRepository;

    private final ParticipantQueryRepository participantQueryRepository;

    public Page<ParticipantsResDto> getParticipants(Long gatheringId, PageRequest pageRequest) {
        return participantQueryRepository.findAllByGathering(gatheringId, pageRequest);
    }

    public boolean isEntry(Gathering gathering, Member member) {
        return participantRepository.findByGatheringAndMember(gathering, member).isPresent();
    }
}
