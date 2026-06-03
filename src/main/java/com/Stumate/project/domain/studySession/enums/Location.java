package com.Stumate.project.domain.studySession.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Location {
    STUDY_CAFE("스터디카페"),
    CAFE("카페"),
    HOME("집"),
    SCHOOL("학교"),
    LIBRARY("도서관"),
    OTHER_PLACES("기타 장소");

    private final String label;
}
