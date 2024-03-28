package org.noint.gathering.domain.gathering.enums.gathering;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.noint.gathering.exception.ExceptionBody;

@Getter
@RequiredArgsConstructor
public enum GatheringExceptionBody implements ExceptionBody {
    NOT_FOUND_GATHERING(404, "모임을 찾을 수 없습니다."),
    CAPACITY_EXCEEDED(400, "참가 인원 초과"),
    ;

    private final int code;
    private final String message;
}
