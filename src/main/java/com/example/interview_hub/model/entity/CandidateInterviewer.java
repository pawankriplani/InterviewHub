package com.example.interview_hub.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Candidate_Interviewer")
public class CandidateInterviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_interview_id")
    private CandidateInterview candidateInterview;

    @Column(name = "interviewer_id")
    private String interviewerId;

    @Column(name = "interviewer_email", length = 100)
    private String interviewerEmail;

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

    public String getInterviewerId() {
        return interviewerId;
    }

    public void setInterviewerId(String interviewerId) {
        this.interviewerId = interviewerId;
    }

    public String getInterviewerEmail() {
        return interviewerEmail;
    }

    public void setInterviewerEmail(String interviewerEmail) {
        this.interviewerEmail = interviewerEmail;
    }
}
