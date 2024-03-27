package org.noint.gathering.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.noint.gathering.exception.ExceptionBody;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionBody implements ExceptionBody {
    EMAIL_DUPLICATE(400, "중복된 이메일"),
    NAME_DUPLICATE(400, "중복된 닉네임"),
    ;

    private final int code;
    private final String message;
}
