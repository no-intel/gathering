package org.noint.gathering.domain.gathering.exception;

import org.noint.gathering.domain.gathering.enums.GatheringExceptionBody;
import org.noint.gathering.exception.GeneralException;

public class GatheringException extends GeneralException {
    public GatheringException(GatheringExceptionBody e) {
        super(e);
    }
}
