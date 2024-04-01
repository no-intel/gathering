package org.noint.gathering.domain.reservation.exception;

import org.noint.gathering.domain.reservation.enums.ReservationExceptionBody;
import org.noint.gathering.exception.GeneralException;

public class ReservationException extends GeneralException {
    public ReservationException(ReservationExceptionBody e) {
        super(e);
    }
}
