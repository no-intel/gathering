package org.noint.gathering.domain.reservation.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record RoomScheduleReqDto(
        @NotNull(message = "날짜는 필 수 값입니다.")
        LocalDate date
) {
}
