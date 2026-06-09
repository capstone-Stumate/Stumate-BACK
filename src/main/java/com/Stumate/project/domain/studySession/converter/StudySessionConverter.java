package com.Stumate.project.domain.studySession.converter;

import com.Stumate.project.domain.studySession.dto.StudySessionReqDTO;
import com.Stumate.project.domain.studySession.dto.StudySessionResDTO;
import com.Stumate.project.domain.studySession.entity.StudySession;

public class StudySessionConverter {

    public static StudySession toEntity(Long userId, StudySessionReqDTO.Start request) {
        return StudySession.builder()
                .userId(userId)
                .userSubjectId(request.getUserSubjectId())
                .location(request.getLocation())
                .build();
    }

    public static StudySessionResDTO.SessionInfo toInfo(StudySession session) {
        return StudySessionResDTO.SessionInfo.builder()
                .sessionId(session.getSessionId())
                .userSubjectId(session.getUserSubjectId())
                .location(session.getLocation())
                .startedAt(session.getStartedAt())
                .endedAt(session.getEndedAt())
                .durationSec(session.getDurationSec())
                .focusScore(session.getFocusScore())
                .pauseCount(session.getPauseCount())
                .build();
    }
}