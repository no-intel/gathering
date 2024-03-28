package org.noint.gathering.domain.gathering.service.participant;

import lombok.RequiredArgsConstructor;
import org.noint.gathering.domain.gathering.repository.participant.ParticipantRepository;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParticipantQueryService {

    private final ParticipantRepository participantRepository;

    public boolean isEntry(Gathering gathering, Member member) {
        return participantRepository.findByGatheringAndMember(gathering, member).isPresent();
    }
}
