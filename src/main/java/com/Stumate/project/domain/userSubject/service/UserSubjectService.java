package com.Stumate.project.domain.userSubject.service;

import com.Stumate.project.domain.userSubject.converter.UserSubjectConverter;
import com.Stumate.project.domain.userSubject.dto.UserSubjectReqDTO;
import com.Stumate.project.domain.userSubject.dto.UserSubjectResDTO;
import com.Stumate.project.domain.userSubject.entity.UserSubject;
import com.Stumate.project.domain.userSubject.repository.UserSubjectRepository;
import com.Stumate.project.global.exception.GlobalException;
import com.Stumate.project.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSubjectService {

    private final UserSubjectRepository userSubjectRepository;

    public List<UserSubjectResDTO.Info> getSubjects(Long userId) {
        return userSubjectRepository.findByUserIdAndDeletedAtIsNull(userId).stream()
                .map(UserSubjectConverter::toInfo)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserSubjectResDTO.Info addSubject(Long userId, UserSubjectReqDTO.Create request) {
        if (userSubjectRepository.existsByUserIdAndSubjectNameAndDeletedAtIsNull(userId, request.getSubjectName())) {
            throw new GlobalException(ErrorCode.DUPLICATE_SUBJECT);
        }
        UserSubject userSubject = UserSubjectConverter.toEntity(userId, request);
        return UserSubjectConverter.toInfo(userSubjectRepository.save(userSubject));
    }

    @Transactional
    public void deleteSubject(Long userId, Long userSubjectId) {
        UserSubject userSubject = userSubjectRepository.findById(userSubjectId)
                .orElseThrow(() -> new GlobalException(ErrorCode.SUBJECT_NOT_FOUND));
        userSubject.delete();
    }
}