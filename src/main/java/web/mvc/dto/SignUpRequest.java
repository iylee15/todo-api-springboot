package web.mvc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@Schema(title = "SingUpRequest : 회원가입 요청 DTO")
public class SignUpRequest {
    @NotNull(message = "아이디를 입력해주세요")
    @NotBlank(message = "공백은 입력할 수 없습니다")
    @Pattern(regexp = "^[^\\s]+$", message = "공백을 포함할 수 없습니다")
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요")
    @NotBlank(message = "공백은 입력할 수 없습니다")
    @Pattern(regexp = "^[^\\s]+$", message = "공백을 포함할 수 없습니다")
    private String password;

    @NotNull(message = "닉네임을 입력해주세요")
    @NotBlank(message = "공백은 입력할 수 없습니다")
    @Pattern(regexp = "^[^\\s]+$", message = "공백을 포함할 수 없습니다")
    private String nickname;
}