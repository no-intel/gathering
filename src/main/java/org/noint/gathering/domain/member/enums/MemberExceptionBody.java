package org.noint.gathering.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.noint.gathering.exception.ExceptionBody;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionBody implements ExceptionBody {
    EMAIL_DUPLICATE(400, "중복된 이메일"),
    NAME_DUPLICATE(400, "중복된 닉네임"),
    LOGIN_FAILED(400, "로그인 실패"),
    NOT_FOUND_MEMBER(404, "유저를 찾을 수 없습니다."),
    FORBIDDEN(403, "권한이 없습니다."),
    ;

    private final int code;
    private final String message;
}
