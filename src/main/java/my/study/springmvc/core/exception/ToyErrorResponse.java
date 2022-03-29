package my.study.springmvc.core.exception;

import lombok.Getter;
import lombok.ToString;
import org.slf4j.MDC;

@ToString
@Getter
public class ToyErrorResponse {
    private final String result = "failure";
    private final String reason;
    private final String traceId = MDC.get("traceId");

    public ToyErrorResponse(final String reason) {
        this.reason = reason;
    }
}
