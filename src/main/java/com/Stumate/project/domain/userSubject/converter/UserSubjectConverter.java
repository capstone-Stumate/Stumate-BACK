package com.Stumate.project.domain.userSubject.converter;

import com.Stumate.project.domain.userSubject.dto.UserSubjectDTO;
import com.Stumate.project.domain.userSubject.entity.UserSubject;

public class UserSubjectConverter {

    public static UserSubjectDTO.Info toInfo(UserSubject userSubject) {
        return UserSubjectDTO.Info.builder()
                .userSubjectId(userSubject.getUserSubjectId())
                .subjectName(userSubject.getSubjectName())
                .build();
    }
}
