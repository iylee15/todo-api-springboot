package web.mvc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_seq")
    private long todoSeq;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(name = "priority", nullable = true)
    private Integer priority;

    @Column(name = "status", nullable = false)
    @ColumnDefault("0")
    private boolean status;

    @Column(name = "date")
    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_seq", nullable = false)
//    private User user;

    @Override
    public String toString() {
        return "Todo 정보 : " + this.getTodoSeq() + " | " + this.getTitle();
    }

    public Todo (String title, String description, boolean status) {
        this.title = title;
        this.description = description;
        this.status = status;
//        this.user = user;
    }
}
