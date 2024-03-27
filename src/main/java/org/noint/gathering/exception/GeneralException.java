package org.noint.gathering.exception;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private int code;
    private String message;

    public GeneralException(ExceptionBody e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }
}
