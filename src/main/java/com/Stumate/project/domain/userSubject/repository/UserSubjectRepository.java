package com.Stumate.project.domain.userSubject.repository;

import com.Stumate.project.domain.userSubject.entity.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubjectRepository extends JpaRepository<UserSubject, Long> {
    List<UserSubject> findByUserIdAndDeletedAtIsNull(Long userId);
    boolean existsByUserIdAndSubjectNameAndDeletedAtIsNull(Long userId, String subjectName);
}
