package com.Stumate.project.domain.studySession.repository;

import com.Stumate.project.domain.studySession.entity.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    List<StudySession> findByUserId(Long userId);
    Optional<StudySession> findBySessionIdAndUserId(Long sessionId, Long userId);
    Optional<StudySession> findByUserIdAndEndedAtIsNull(Long userId);
    List<StudySession> findByUserIdAndStartedAtBetween(Long userId, LocalDateTime from, LocalDateTime to);

    @Query("SELECT SUM(s.durationSec) FROM StudySession s WHERE s.userId = :userId AND s.startedAt BETWEEN :from AND :to")
    Long sumDurationSecByUserIdAndPeriod(@Param("userId") Long userId,
                                         @Param("from") LocalDateTime from,
                                         @Param("to") LocalDateTime to);
}