package web.mvc.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class SignUpRequest {
    private String userId;
    private String password;
    private String nickname;
}
