package com.Stumate.project.domain.todo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Todos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long todoId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "todo_date", nullable = false)
    private LocalDate todoDate;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.isCompleted == null) this.isCompleted = false;
    }

    public void complete() {
        this.isCompleted = true;
        this.completedAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
