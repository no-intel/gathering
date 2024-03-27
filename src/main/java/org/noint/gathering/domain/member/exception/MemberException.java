package org.noint.gathering.domain.member.exception;

import org.noint.gathering.domain.member.enums.MemberExceptionBody;
import org.noint.gathering.exception.GeneralException;

public class MemberException extends GeneralException {
    public MemberException(MemberExceptionBody e) {
        super(e);
    }
}
