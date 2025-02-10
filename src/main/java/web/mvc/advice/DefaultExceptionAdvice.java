package web.mvc.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web.mvc.exception.ErrorCode;
import web.mvc.exception.UserException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionAdvice {
    @ExceptionHandler({UserException.class})
    public ProblemDetail ExceptionHandle(UserException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(e.getErrorCode().getHttpStatus());

        problemDetail.setTitle(e.getErrorCode().getTitle());
        problemDetail.setDetail(e.getErrorCode().getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return  problemDetail;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.info(errorMessage);
        return new ResponseEntity<>(ErrorCode.BLANK, HttpStatus.BAD_REQUEST);
    }
}
