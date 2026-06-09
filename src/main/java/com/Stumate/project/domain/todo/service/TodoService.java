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

    public List<TodoResDTO.TodoInfo> getTodayTodos(Long userId) {
        return todoRepository.findAllByUserIdAndTodoDate(userId, LocalDate.now()).stream()
                .map(TodoConverter::toInfo)
                .collect(Collectors.toList());
    }

    public List<TodoResDTO.DailyGroup> getWeeklyTodos(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Todo> todos = todoRepository
                .findAllByUserIdAndTodoDateBetweenOrderByTodoDateAsc(userId, startDate, endDate);
        return TodoConverter.toDailyGroups(todos);
    }

    @Transactional
    public TodoResDTO.TodoInfo createTodo(Long userId, TodoReqDTO.Create request) {
        Todo todo = TodoConverter.toEntity(userId, request);
        return TodoConverter.toInfo(todoRepository.save(todo));
    }

    @Transactional
    public TodoResDTO.TodoInfo completeTodo(Long userId, Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GlobalException(ErrorCode.TODO_NOT_FOUND));
        todo.complete();
        return TodoConverter.toInfo(todo);
    }

    @Transactional
    public void deleteTodo(Long userId, Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new GlobalException(ErrorCode.TODO_NOT_FOUND));
        todoRepository.delete(todo);
    }
}