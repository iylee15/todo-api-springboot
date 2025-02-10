package web.mvc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    BLANK(HttpStatus.BAD_REQUEST, "Blank is not permitted", "공백은 입력할 수 없습니다");

    private final HttpStatus httpStatus;
    private final String title;
    private final String message;
}
