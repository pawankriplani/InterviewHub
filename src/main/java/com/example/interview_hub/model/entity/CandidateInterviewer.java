package com.example.interview_hub.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Candidate_Interviewers")
public class CandidateInterviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidate_interview_id", nullable = false)
    private CandidateInterview candidateInterview;

    @ManyToOne
    @JoinColumn(name = "interviewer_id", nullable = false)
    private Interviewer interviewer;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CandidateInterview getCandidateInterview() {
        return candidateInterview;
    }

    public void setCandidateInterview(CandidateInterview candidateInterview) {
        this.candidateInterview = candidateInterview;
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(Interviewer interviewer) {
        this.interviewer = interviewer;
    }
}
