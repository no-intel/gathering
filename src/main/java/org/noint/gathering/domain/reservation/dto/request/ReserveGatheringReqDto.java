package org.noint.gathering.domain.reservation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReserveGatheringReqDto(
        @NotNull(message = "모임 값은 필 수 입니다.")
        Long gatheringId,

        @NotNull(message = "최소 1개 이상의 스케줄이 필요 합니다.")
        List<Long> roomScheduleIds
) {
}
