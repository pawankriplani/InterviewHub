package com.example.interview_hub.repository;

import com.example.interview_hub.model.entity.CandidateInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CandidateInterviewRepository extends JpaRepository<CandidateInterview, Integer> {
    Optional<CandidateInterview> findByCandidateCandidateIdAndRoundRoundId(Integer candidateId, Integer roundId);

    @Modifying
    @Query("UPDATE CandidateInterview ci SET ci.status = 'Completed' " +
           "WHERE ci.endMeetingTs <= :currentTime")
    void updateCompletedInterviews(@Param("currentTime") LocalDateTime currentTime);

}
