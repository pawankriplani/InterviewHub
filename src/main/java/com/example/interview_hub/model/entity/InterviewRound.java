package com.example.interview_hub.model.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Interview_Rounds")
public class InterviewRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roundId;

    @Column(length = 100)
    private String roundName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "round")
    private Set<CandidateInterview> candidateInterviews;

    // Getters and Setters
    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CandidateInterview> getCandidateInterviews() {
        return candidateInterviews;
    }

    public void setCandidateInterviews(Set<CandidateInterview> candidateInterviews) {
        this.candidateInterviews = candidateInterviews;
    }
}
