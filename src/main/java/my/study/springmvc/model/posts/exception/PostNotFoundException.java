package my.study.springmvc.model.posts.exception;

import lombok.Getter;
import my.study.springmvc.core.exception.ToyApplicationException;
import my.study.springmvc.core.exception.i18nErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class PostNotFoundException extends ToyApplicationException {
    private static final i18nErrorCode errorCode = i18nErrorCode.POST_NOT_FOUND;
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public PostNotFoundException() {
        super(errorCode, httpStatus);
    }
}
