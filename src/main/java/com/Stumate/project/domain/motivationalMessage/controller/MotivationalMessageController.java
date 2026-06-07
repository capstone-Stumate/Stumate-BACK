package com.Stumate.project.domain.motivationalMessage.controller;

import com.Stumate.project.domain.motivationalMessage.service.MotivationalMessageService;
import com.Stumate.project.domain.todo.repository.TodoRepository;
import com.Stumate.project.domain.user.entity.User;
import com.Stumate.project.domain.user.repository.UserRepository;
import com.Stumate.project.global.exception.GlobalException;
import com.Stumate.project.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/message")
@RequiredArgsConstructor
public class MotivationalMessageController {

    private final MotivationalMessageService motivationalMessageService;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    // 앱 열 때마다 호출 → AI 응원 메시지 생성
    @GetMapping
    public ResponseEntity<String> getMessage(@PathVariable Long userId) {

        // 사용자 이름 조회
        User user = userRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        // 이번주 수행률 계산
        LocalDate weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate weekEnd = LocalDate.now().with(DayOfWeek.SUNDAY);

        List<?> weeklyTodos = todoRepository.findAllByUserIdAndTodoDateBetweenOrderByTodoDateAsc(
                userId, weekStart, weekEnd);

        double completionRate = 0.0;
        if (!weeklyTodos.isEmpty()) {
            long completed = weeklyTodos.stream()
                    .filter(t -> ((com.Stumate.project.domain.todo.entity.Todo) t).getIsCompleted())
                    .count();
            completionRate = (double) completed / weeklyTodos.size();
        }

        String message = motivationalMessageService.generateMessage(user.getName(), completionRate);
        return ResponseEntity.ok(message);
    }
}