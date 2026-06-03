package com.Stumate.project.domain.motivationalMessage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motivational_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MotivationalMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Long msgId;

    @Column(name = "content", nullable = false, length = 200)
    private String content;
}
