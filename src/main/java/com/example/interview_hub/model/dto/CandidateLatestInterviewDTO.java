package com.example.interview_hub.model.dto;

public class CandidateLatestInterviewDTO {
    private Integer candidateId;
    private String candidateName;
    private Integer roundId;
    private String roundName;
    private String feedback;
    private String status;

    public CandidateLatestInterviewDTO(Integer candidateId, String candidateName, Integer roundId, String roundName, String feedback, String status) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.roundId = roundId;
        this.roundName = roundName;
        this.feedback = feedback;
        this.status = status;
    }

    // Getters and setters

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
