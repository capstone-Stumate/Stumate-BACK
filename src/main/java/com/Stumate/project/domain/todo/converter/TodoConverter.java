package com.Stumate.project.domain.todo.converter;

import com.Stumate.project.domain.todo.dto.TodoReqDTO;
import com.Stumate.project.domain.todo.dto.TodoResDTO;
import com.Stumate.project.domain.todo.entity.Todo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TodoConverter {

    public static TodoResDTO.TodoInfo toInfo(Todo todo) {
        return TodoResDTO.TodoInfo.builder()
                .todoId(todo.getTodoId())
                .content(todo.getContent())
                .todoDate(todo.getTodoDate())
                .isCompleted(todo.getIsCompleted())
                .completedAt(todo.getCompletedAt())
                .createdAt(todo.getCreatedAt())
                .build();
    }

    public static Todo toEntity(Long userId, TodoReqDTO.Create request) {
        return Todo.builder()
                .userId(userId)
                .content(request.getContent())
                .todoDate(request.getTodoDate())
                .build();
    }

    public static List<TodoResDTO.DailyGroup> toDailyGroups(List<Todo> todos) {
        Map<LocalDate, List<Todo>> grouped = todos.stream()
                .collect(Collectors.groupingBy(Todo::getTodoDate));

        return grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> TodoResDTO.DailyGroup.builder()
                        .todoDate(entry.getKey())
                        .todos(entry.getValue().stream()
                                .map(TodoConverter::toInfo)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}