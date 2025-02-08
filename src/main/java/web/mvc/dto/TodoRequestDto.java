package web.mvc.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequestDto {
    private long todoSeq;
    private String title;
    private String description;
    private int priority;
    private int status;
    private LocalDate date;
    private long userSeq;
}
