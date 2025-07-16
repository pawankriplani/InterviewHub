package com.example.interview_hub.repository;

import com.example.interview_hub.model.entity.InterviewRound;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRoundRepository extends JpaRepository<InterviewRound, Integer> {
    
    @EntityGraph(attributePaths = {"candidateInterviews", "candidateInterviews.candidate", "candidateInterviews.candidateInterviewers", "candidateInterviews.candidateInterviewers.interviewer"})
    List<InterviewRound> findAll();
}
