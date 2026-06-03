package com.Stumate.project.domain.todo.controller;

import com.Stumate.project.domain.todo.dto.TodoReqDTO;
import com.Stumate.project.domain.todo.dto.TodoResDTO;
import com.Stumate.project.domain.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // 오늘 할 일 조회
    @GetMapping("/today")
    public ResponseEntity<List<TodoResDTO.Info>> getTodayTodos(@PathVariable Long userId) {
        return ResponseEntity.ok(todoService.getTodayTodos(userId));
    }

    // 주간 할 일 조회 (내 정보 화면)
    @GetMapping("/weekly")
    public ResponseEntity<List<TodoResDTO.DailyGroup>> getWeeklyTodos(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(todoService.getWeeklyTodos(userId, startDate, endDate));
    }

    // 할 일 추가
    @PostMapping
    public ResponseEntity<TodoResDTO.Info> createTodo(
            @PathVariable Long userId,
            @Valid @RequestBody TodoReqDTO.Create request) {
        return ResponseEntity.ok(todoService.createTodo(userId, request));
    }

    // 할 일 완료 처리
    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<TodoResDTO.Info> completeTodo(
            @PathVariable Long userId,
            @PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.completeTodo(userId, todoId));
    }

}
