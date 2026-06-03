package com.Stumate.project.domain.motivationalMessage.repository;

import com.Stumate.project.domain.motivationalMessage.entity.MotivationalMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MotivationalMessageRepository extends JpaRepository<MotivationalMessage, Long> {

    @Query(value = "SELECT * FROM motivational_messages ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<MotivationalMessage> findRandom();
}
