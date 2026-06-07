package com.Stumate.project.domain.userSubject.converter;

import com.Stumate.project.domain.userSubject.dto.UserSubjectReqDTO;
import com.Stumate.project.domain.userSubject.dto.UserSubjectResDTO;
import com.Stumate.project.domain.userSubject.entity.UserSubject;

public class UserSubjectConverter {

    public static UserSubject toEntity(Long userId, UserSubjectReqDTO.Create request) {
        return UserSubject.builder()
                .userId(userId)
                .subjectName(request.getSubjectName())
                .build();
    }

    public static UserSubjectResDTO.Info toInfo(UserSubject userSubject) {
        return UserSubjectResDTO.Info.builder()
                .userSubjectId(userSubject.getUserSubjectId())
                .subjectName(userSubject.getSubjectName())
                .build();
    }
}