package org.noint.gathering.domain.gathering.service.participant;

import lombok.RequiredArgsConstructor;
import org.noint.gathering.domain.gathering.enums.GatheringExceptionBody;
import org.noint.gathering.domain.gathering.exception.GatheringException;
import org.noint.gathering.domain.gathering.repository.participant.ParticipantRepository;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.noint.gathering.entity.Participant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantCommendService {

    private final ParticipantQueryService participantQueryService;

    private final ParticipantRepository participantRepository;

    public void register(Member member, Gathering gathering) {
        checkDuplicateEntry(member, gathering);
        participantRepository.save(new Participant(gathering, member));
    }

    private void checkDuplicateEntry(Member member, Gathering gathering) {
        if (participantQueryService.isEntry(gathering, member)) {
            throw new GatheringException(GatheringExceptionBody.ALREADY_ENTRY);
        }
    }
}
