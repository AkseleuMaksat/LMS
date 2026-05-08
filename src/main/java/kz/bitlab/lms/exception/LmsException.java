package kz.bitlab.lms.exception;

import kz.bitlab.lms.enums.ExceptionStatus;
import lombok.Getter;

@Getter
public class LmsException extends RuntimeException {
    private final ExceptionStatus status;

    public LmsException(String message, ExceptionStatus status) {
        super(message);
        this.status = status;
    }
}
