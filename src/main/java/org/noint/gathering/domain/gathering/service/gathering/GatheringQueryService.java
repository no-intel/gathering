package org.noint.gathering.domain.gathering.service.gathering;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.dto.response.ParticipantsResDto;
import org.noint.gathering.domain.gathering.exception.GatheringException;
import org.noint.gathering.domain.gathering.repository.gathering.GatheringRepository;
import org.noint.gathering.domain.gathering.repository.participant.ParticipantQueryRepository;
import org.noint.gathering.entity.Gathering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.noint.gathering.domain.gathering.enums.GatheringExceptionBody.NOT_FOUND_GATHERING;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GatheringQueryService {

    private final GatheringRepository gatheringRepository;

    public Gathering getGathering(Long gatheringId) {
        return gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new GatheringException(NOT_FOUND_GATHERING));
    }
}
