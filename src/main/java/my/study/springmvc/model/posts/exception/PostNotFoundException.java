package my.study.springmvc.model.posts.exception;

import lombok.Getter;
import my.study.springmvc.core.exception.ToyApplicationException;
import org.springframework.http.HttpStatus;

@Getter
public class PostNotFoundException extends ToyApplicationException {
    private static final String message = "게시글이 존재하지 않습니다.";
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public PostNotFoundException() {
        super(message, httpStatus);
    }

    public PostNotFoundException(Long id) {
        super(message + " id : " + id, httpStatus);
    }
}
