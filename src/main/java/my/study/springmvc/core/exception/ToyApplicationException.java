package my.study.springmvc.core.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ToyApplicationException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ToyApplicationException(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
    }

    public ToyApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ToyApplicationException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
