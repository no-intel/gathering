package org.noint.gathering.domain.gathering.exception.gathering;

import org.noint.gathering.domain.gathering.enums.gathering.GatheringExceptionBody;
import org.noint.gathering.domain.member.enums.MemberExceptionBody;
import org.noint.gathering.exception.GeneralException;

public class GatheringException extends GeneralException {
    public GatheringException(GatheringExceptionBody e) {
        super(e);
    }
}
