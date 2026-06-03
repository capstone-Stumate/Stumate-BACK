package com.Stumate.project.domain.studySession.converter;

import com.Stumate.project.domain.studySession.dto.StudySessionReqDTO;
import com.Stumate.project.domain.studySession.dto.StudySessionResDTO;
import com.Stumate.project.domain.studySession.entity.StudySession;

public class StudySessionConverter {

    public static StudySession toEntity(Long userId, StudySessionReqDTO.Start request) {
        return StudySession.builder()
                .userId(userId)
                .subject(request.getSubject())
                .location(request.getLocation())
                .build();
    }

    public static StudySessionResDTO.Info toInfo(StudySession session) {
        return StudySessionResDTO.Info.builder()
                .sessionId(session.getSessionId())
                .subject(session.getSubject())
                .location(session.getLocation())
                .startedAt(session.getStartedAt())
                .endedAt(session.getEndedAt())
                .durationSec(session.getDurationSec())
                .focusScore(session.getFocusScore())
                .pauseCount(session.getPauseCount())
                .build();
    }
}