package my.study.springmvc.model.comments.exception;

import my.study.springmvc.core.exception.ToyApplicationException;
import my.study.springmvc.core.exception.i18nErrorCode;
import org.springframework.http.HttpStatus;

public class CommentReplyDepth extends ToyApplicationException {
    private static final i18nErrorCode errorCode = i18nErrorCode.COMMENT_REPLY_DEPTH;
    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public CommentReplyDepth() {
        this(errorCode, httpStatus);
    }

    public CommentReplyDepth(i18nErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}
