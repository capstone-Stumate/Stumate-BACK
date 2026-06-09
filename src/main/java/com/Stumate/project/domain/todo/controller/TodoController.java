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

    @GetMapping("/today")
    public ResponseEntity<List<TodoResDTO.TodoInfo>> getTodayTodos(@PathVariable Long userId) {
        return ResponseEntity.ok(todoService.getTodayTodos(userId));
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<TodoResDTO.DailyGroup>> getWeeklyTodos(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(todoService.getWeeklyTodos(userId, startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<TodoResDTO.TodoInfo> createTodo(
            @PathVariable Long userId,
            @Valid @RequestBody TodoReqDTO.Create request) {
        return ResponseEntity.ok(todoService.createTodo(userId, request));
    }

    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<TodoResDTO.TodoInfo> completeTodo(
            @PathVariable Long userId,
            @PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.completeTodo(userId, todoId));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long userId,
            @PathVariable Long todoId) {
        todoService.deleteTodo(userId, todoId);
        return ResponseEntity.noContent().build();
    }
}