package web.mvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private long todoSeq;

    @NotNull(message = "내용을 입력해주세요")
    private String title;
    private String description;
    private int priority;
    private int status;
    private LocalDate date;
    private long userSeq;
}
