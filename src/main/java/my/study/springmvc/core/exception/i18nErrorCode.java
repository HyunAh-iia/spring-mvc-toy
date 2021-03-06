package my.study.springmvc.core.exception;

import lombok.Getter;

@Getter
public enum i18nErrorCode {
    POST_NOT_FOUND("post.not-found"),
    COMMENT_NOT_FOUND("comment.not-found"),
    COMMENT_REPLY_DEPTH("comment.reply-depth"),
    ;

    private final String code;

    i18nErrorCode(final String code) {
        this.code = code;
    }
}
