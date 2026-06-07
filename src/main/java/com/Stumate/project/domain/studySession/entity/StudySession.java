package com.Stumate.project.domain.studySession.entity;

import com.Stumate.project.domain.studySession.enums.LocationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "timer_record_sessions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_subject_id", nullable = false)
    private Long userSubjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "location", nullable = false)
    private LocationType location;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "duration_sec")
    private Integer durationSec;

    @Column(name = "focus_score")
    private Integer focusScore;

    @Column(name = "pause_count")
    private Integer pauseCount;

    @PrePersist
    protected void onCreate() {
        if (this.pauseCount == null) this.pauseCount = 0;
        this.startedAt = LocalDateTime.now();
    }

    public void finish(LocalDateTime endedAt, Integer durationSec, Integer focusScore, Integer pauseCount) {
        this.endedAt = endedAt;
        this.durationSec = durationSec;
        this.focusScore = focusScore;
        this.pauseCount = pauseCount;
    }
}
