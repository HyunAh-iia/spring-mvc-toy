package my.study.springmvc.core.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ToyApplicationException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final i18nErrorCode errorCode;

    public ToyApplicationException(i18nErrorCode errorCode, HttpStatus httpStatus) {
        this(errorCode, null, httpStatus);
    }

    public ToyApplicationException(i18nErrorCode errorCode, Throwable cause, HttpStatus httpStatus) {
        super(null, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
