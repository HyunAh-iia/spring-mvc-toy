package my.study.springmvc.controller;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.core.exception.ToyApplicationException;
import my.study.springmvc.core.exception.ToyErrorResponse;
import my.study.springmvc.core.exception.i18nErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@RestControllerAdvice
public class RestControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    private final MessageSource messageSource;

    @ExceptionHandler(ToyApplicationException.class)
    public ResponseEntity<ToyErrorResponse> handleToyApplicationException(ToyApplicationException ex) {
        ToyErrorResponse errorResponse = new ToyErrorResponse(getI18nErrorMessage(ex.getErrorCode()));
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ToyErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ToyErrorResponse errorResponse = new ToyErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, WebExchangeBindException.class})
    public ResponseEntity<ToyErrorResponse> handleMethodArgumentNotValidExceptionOrBindingException(
            Exception ex
    ) {
        final BindingResult bindingResult;
        final Set<String> errors = new HashSet<>();

        if (ex instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
        } else if (ex instanceof WebExchangeBindException) {
            bindingResult = ((WebExchangeBindException) ex).getBindingResult();
        } else {
            bindingResult = ((BindException) ex).getBindingResult();
        }

        bindingResult.getFieldErrors().forEach(fieldError -> errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage()));

        ToyErrorResponse errorResponse = new ToyErrorResponse(String.join("\n", errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ToyErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ToyErrorResponse errorResponse = new ToyErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ToyErrorResponse> handleException(Exception ex, WebRequest request) {
        logger.error("!@@ Unknown Exception: {}", ex);
        logger.error("!@@ Unknown Exception Request: {}", request);
        ToyErrorResponse errorResponse = new ToyErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private String getI18nErrorMessage(final i18nErrorCode errorMessage) {
        return messageSource.getMessage(errorMessage.getCode(), null, LocaleContextHolder.getLocale());
    }
}
