package com.example.interview_hub.model.dto;

import java.util.List;

public class InterviewRoundResponse {
    private String roundName;
    private Integer roundId;
    private List<CandidateResponse> candidates;

    // Getters and Setters
    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public List<CandidateResponse> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateResponse> candidates) {
        this.candidates = candidates;
    }
}
