package org.noint.gathering.domain.reservation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.noint.gathering.exception.ExceptionBody;

@Getter
@RequiredArgsConstructor
public enum ReservationExceptionBody implements ExceptionBody {
    OUT_OF_RANGE_DATE(400, "검색 날짜 범위를 벗어 났습니다."),
    ;

    private final int code;
    private final String message;
}
