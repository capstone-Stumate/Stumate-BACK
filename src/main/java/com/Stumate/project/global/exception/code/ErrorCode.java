package com.Stumate.project.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),

    // FixedSchedule
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."),

    // StudySession
    SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "공부 세션을 찾을 수 없습니다."),
    SESSION_ALREADY_STARTED(HttpStatus.BAD_REQUEST, "이미 시작된 세션이 있습니다."),

    // Todo
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "할 일을 찾을 수 없습니다."),

    // UserSubject (추가!)
    SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "과목을 찾을 수 없습니다."),
    DUPLICATE_SUBJECT(HttpStatus.CONFLICT, "이미 등록된 과목입니다.");

    private final HttpStatus status;
    private final String message;
}