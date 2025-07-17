package com.example.interview_hub.repository;

import com.example.interview_hub.model.entity.CandidateInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateInterviewRepository extends JpaRepository<CandidateInterview, Integer> {
}
