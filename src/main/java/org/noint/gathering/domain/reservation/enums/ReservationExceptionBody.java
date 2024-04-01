package org.noint.gathering.domain.reservation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.noint.gathering.exception.ExceptionBody;

@Getter
@RequiredArgsConstructor
public enum ReservationExceptionBody implements ExceptionBody {
    OUT_OF_RANGE_DATE(400, "검색 날짜 범위를 벗어 났습니다."),
    NOT_FOUND_ROOM_SCHEDULE(404, "룸 스케줄을 찾을 수 없습니다."),
    UNAVAILABLE_RESERVE(400, "예약이 불가능 합니다."),
    FORBIDDEN(403, "권한이 없습니다."),
    DUPLICATE_REQUEST(400, "중복된 요청 입니다."),
    ;

    private final int code;
    private final String message;
}
