package com.Stumate.project.domain.todo.repository;

import com.Stumate.project.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserIdAndTodoDate(Long userId, LocalDate todoDate);
    List<Todo> findByUserIdAndTodoDateBetween(Long userId, LocalDate from, LocalDate to);
    Optional<Todo> findByTodoIdAndUserId(Long todoId, Long userId);

    // 추가!
    List<Todo> findAllByUserIdAndTodoDate(Long userId, LocalDate todoDate);
    List<Todo> findAllByUserIdAndTodoDateBetweenOrderByTodoDateAsc(Long userId, LocalDate from, LocalDate to);
}