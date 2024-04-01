package org.noint.gathering.domain.reservation.dto.request;

import java.util.List;

public record ReserveGatheringReqDto(
        Long gatheringId,
        List<Long> roomScheduleIds
) {
}
