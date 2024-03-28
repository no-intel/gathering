package org.noint.gathering.domain.gathering.service.gathering;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.exception.gathering.GatheringException;
import org.noint.gathering.domain.gathering.repository.gathering.GatheringRepository;
import org.noint.gathering.entity.Gathering;
import org.springframework.stereotype.Service;

import static org.noint.gathering.domain.gathering.enums.gathering.GatheringExceptionBody.NOT_FOUND_Gathering;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatheringQueryService {

    private final GatheringRepository gatheringRepository;

    public Gathering getGathering(Long gatheringId) {
        return gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new GatheringException(NOT_FOUND_Gathering));
    }
}
