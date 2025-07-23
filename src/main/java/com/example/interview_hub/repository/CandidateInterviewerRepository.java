package com.example.interview_hub.repository;

import com.example.interview_hub.model.entity.CandidateInterviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateInterviewerRepository extends JpaRepository<CandidateInterviewer, Integer> {
    List<CandidateInterviewer> findByCandidateInterviewCandidateInterviewId(Integer candidateInterviewId);
}
