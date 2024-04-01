package org.noint.gathering.domain.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CancelReservationReqDto(
        @NotBlank(message = "요청 ID 값은 필수 입니다.")
        String requestId
) {
}
