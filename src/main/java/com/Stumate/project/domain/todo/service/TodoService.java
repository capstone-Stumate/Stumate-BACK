package com.Stumate.project.domain.todo.service;

import com.Stumate.project.global.exception.GlobalException;
import com.Stumate.project.global.exception.code.ErrorCode;
import com.Stumate.project.domain.todo.converter.TodoConverter;
import com.Stumate.project.domain.todo.dto.TodoReqDTO;
import com.Stumate.project.domain.todo.dto.TodoResDTO;
import com.Stumate.project.domain.todo.entity.Todo;
import com.Stumate.project.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    // 오늘 할 일 조회 (타이머 메인 화면)
    public List<TodoResDTO.Info> getTodayTodos(Long userId) {
        return todoRepository.findAllByUserIdAndTodoDate(userId, LocalDate.now()).stream()
                .map(TodoConverter::toInfo)
                .collect(Collectors.toList());
    }

    // 주간 할 일 조회 (내 정보 화면)
    public List<TodoResDTO.DailyGroup> getWeeklyTodos(Long userId,
                                                       LocalDate startDate,
                                                       LocalDate endDate) {
        List<Todo> todos = todoRepository
                .findAllByUserIdAndTodoDateBetweenOrderByTodoDateAsc(userId, startDate, endDate);
        return TodoConverter.toDailyGroups(todos);
    }

    // 할 일 추가
    @Transactional
    public TodoResDTO.Info createTodo(Long userId, TodoReqDTO.Create request) {
        Todo todo = TodoConverter.toEntity(userId, request);
        return TodoConverter.toInfo(todoRepository.save(todo));
    }

    // 할 일 완료 처리
    @Transactional
    public TodoResDTO.Info completeTodo(Long userId, Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GlobalException(ErrorCode.TODO_NOT_FOUND));
        todo.complete();
        return TodoConverter.toInfo(todo);
    }

    // 할 일 삭제
    @Transactional
    public void deleteTodo(Long userId, Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GlobalException(ErrorCode.TODO_NOT_FOUND));
        todoRepository.delete(todo);
    }
}
