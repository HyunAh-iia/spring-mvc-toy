package my.study.springmvc.core.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ToyErrorResponse {
    private final String result = "failure";
    private final String reason;

    public ToyErrorResponse(String reason) {
        this.reason = reason;
    }
}
