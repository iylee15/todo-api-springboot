package web.mvc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "TodoDto : Todo CRUD DTO")
public class TodoDto {
    private long todoSeq;

    @NotNull(message = "제목을 입력해주세요")
    private String title;
    private String description;
    private Integer priority;
    private int status;
    private LocalDate date;
}
