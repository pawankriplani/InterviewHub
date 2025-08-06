package com.example.interview_hub.repository;

import com.example.interview_hub.model.entity.CandidateInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.interview_hub.model.dto.CandidateLatestInterviewDTO;
import com.example.interview_hub.model.dto.CandidateLatestInterviewProjection;
import com.example.interview_hub.model.entity.User;

@Repository
public interface CandidateInterviewRepository extends JpaRepository<CandidateInterview, Integer> {
    Optional<CandidateInterview> findByCandidateCandidateIdAndRoundRoundId(Integer candidateId, Integer roundId);

    Optional<CandidateInterview> findByCandidateCandidateIdAndRoundRoundIdAndStatus(Integer candidateId, Integer roundId, String status);

    @Modifying
    @Query("UPDATE CandidateInterview ci SET ci.status = 'Completed' " +
           "WHERE ci.status = 'In progress' and ci.endMeetingTs <= :currentTime")
    void updateCompletedInterviews(@Param("currentTime") LocalDateTime currentTime);

    @Query(value = "SELECT " +
           "ci.candidate_id AS candidateId, " +
           "c.name AS candidateName, " +
           "ir.round_id AS roundId, " +
           "ir.round_name AS roundName, " +
           "ci.feedback AS feedback, " +
           "ci.status AS status " +
           "FROM Candidate_Interviews ci " +
           "JOIN candidates c ON ci.candidate_id = c.candidate_id " +
           "JOIN Interview_Rounds ir ON ci.round_id = ir.round_id " +
           "WHERE c.manager_id = :managerId " +
           "AND ci.candidate_interview_id IN (" +
           "    SELECT MAX(ci2.candidate_interview_id) " +
           "    FROM Candidate_Interviews ci2 " +
           "    JOIN candidates c2 ON ci2.candidate_id = c2.candidate_id " +
           "    WHERE c2.manager_id = :managerId " +
           "    GROUP BY ci2.candidate_id" +
           ") " +
           "ORDER BY ci.candidate_id", nativeQuery = true)
    List<CandidateLatestInterviewProjection> findLatestInterviewsByManagerId(@Param("managerId") Integer managerId);

    @Query(value = "SELECT " +
           "ci.candidate_id AS candidateId, " +
           "c.name AS candidateName, " +
           "ir.round_id AS roundId, " +
           "ir.round_name AS roundName, " +
           "ci.feedback AS feedback, " +
           "ci.status AS status " +
           "FROM Candidate_Interviews ci " +
           "JOIN candidates c ON ci.candidate_id = c.candidate_id " +
           "JOIN Interview_Rounds ir ON ci.round_id = ir.round_id " +
           "WHERE ci.candidate_interview_id IN (" +
           "    SELECT MAX(candidate_interview_id) " +
           "    FROM Candidate_Interviews " +
           "    GROUP BY candidate_id" +
           ") " +
           "ORDER BY ci.candidate_id", nativeQuery = true)
    List<CandidateLatestInterviewProjection> findLatestInterviewsForAllCandidates();
}
