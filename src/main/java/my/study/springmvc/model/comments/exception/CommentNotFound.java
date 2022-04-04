package my.study.springmvc.model.comments.exception;

import my.study.springmvc.core.exception.ToyApplicationException;
import my.study.springmvc.core.exception.i18nErrorCode;
import org.springframework.http.HttpStatus;

public class CommentNotFound extends ToyApplicationException {
    private static final i18nErrorCode errorCode = i18nErrorCode.COMMENT_NOT_FOUND;
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public CommentNotFound() {
        super(errorCode, httpStatus);
    }
}
