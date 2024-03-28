package org.noint.gathering.domain.gathering.enums.gathering;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.noint.gathering.exception.ExceptionBody;

@Getter
@RequiredArgsConstructor
public enum GatheringExceptionBody implements ExceptionBody {
    NOT_FOUND_Gathering(404, "유저를 찾을 수 없습니다."),
    ;

    private final int code;
    private final String message;
}
