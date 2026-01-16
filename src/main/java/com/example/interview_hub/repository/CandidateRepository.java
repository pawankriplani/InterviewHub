package com.example.interview_hub.repository;

import com.example.interview_hub.model.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    @Query("SELECT c FROM Candidate c LEFT JOIN FETCH c.manager WHERE c.candidateId = :candidateId")
    Optional<Candidate> findByIdWithManager(@Param("candidateId") Integer candidateId);
}
