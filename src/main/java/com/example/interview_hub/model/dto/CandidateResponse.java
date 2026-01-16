package com.example.interview_hub.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CandidateResponse {
    private Integer candidateId;
    private String name;
    private String status;
    private String feedback;
    private String jobDetails;
    private List<String> interviewers;
    private LocalDateTime interviewDateTime;
    private ManagerResponse manager;
    private List<InterviewRoundHistory> interviewHistory;
    private Double score;

    public static class InterviewRoundHistory {
        private Integer roundNumber;
        private String roundName;
        private String status;
        private String feedback;

        // Getters and Setters
        public Integer getRoundNumber() {
            return roundNumber;
        }

        public void setRoundNumber(Integer roundNumber) {
            this.roundNumber = roundNumber;
        }

        public String getRoundName() {
            return roundName;
        }

        public void setRoundName(String roundName) {
            this.roundName = roundName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }
    }

    // Getters and Setters
    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(String jobDetails) {
        this.jobDetails = jobDetails;
    }

    public List<String> getInterviewers() {
        return interviewers;
    }

    public void setInterviewers(List<String> interviewers) {
        this.interviewers = interviewers;
    }

    public LocalDateTime getInterviewDateTime() {
        return interviewDateTime;
    }

    public void setInterviewDateTime(LocalDateTime interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public ManagerResponse getManager() {
        return manager;
    }

    public void setManager(ManagerResponse manager) {
        this.manager = manager;
    }

    public List<InterviewRoundHistory> getInterviewHistory() {
        return interviewHistory;
    }

    public void setInterviewHistory(List<InterviewRoundHistory> interviewHistory) {
        this.interviewHistory = interviewHistory;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
