package com.Stumate.project.domain.studySession.service;

import com.Stumate.project.global.exception.GlobalException;
import com.Stumate.project.global.exception.code.ErrorCode;
import com.Stumate.project.domain.studySession.converter.StudySessionConverter;
import com.Stumate.project.domain.studySession.dto.StudySessionReqDTO;
import com.Stumate.project.domain.studySession.dto.StudySessionResDTO;
import com.Stumate.project.domain.studySession.entity.StudySession;
import com.Stumate.project.domain.studySession.repository.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudySessionService {

    private final StudySessionRepository studySessionRepository;

    @Transactional
    public StudySessionResDTO.SessionInfo startSession(Long userId, StudySessionReqDTO.Start request) {
        studySessionRepository.findByUserIdAndEndedAtIsNull(userId).ifPresent(s -> {
            throw new GlobalException(ErrorCode.SESSION_ALREADY_STARTED);
        });
        StudySession session = StudySessionConverter.toEntity(userId, request);
        return StudySessionConverter.toInfo(studySessionRepository.save(session));
    }

    @Transactional
    public StudySessionResDTO.SessionInfo finishSession(Long userId, Long sessionId,
                                                        StudySessionReqDTO.Finish request) {
        StudySession session = studySessionRepository.findBySessionIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.SESSION_NOT_FOUND));
        LocalDateTime now = LocalDateTime.now();
        int durationSec = (int) ChronoUnit.SECONDS.between(session.getStartedAt(), now);
        session.finish(now, durationSec, request.getFocusScore(), request.getPauseCount());
        return StudySessionConverter.toInfo(session);
    }

    public StudySessionResDTO.WeeklyStats getWeeklyStats(Long userId,
                                                         LocalDateTime weekStart,
                                                         LocalDateTime weekEnd) {
        Long totalSec = studySessionRepository.sumDurationSecByUserIdAndPeriod(userId, weekStart, weekEnd);
        return StudySessionResDTO.WeeklyStats.builder()
                .totalDurationSec(totalSec)
                .build();
    }
}