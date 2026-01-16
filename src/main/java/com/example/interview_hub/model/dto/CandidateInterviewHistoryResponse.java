package com.example.interview_hub.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CandidateInterviewHistoryResponse {
    private Integer candidateId;
    private String name;
    private Integer currentRound;
    private List<InterviewRoundHistory> interviewHistory;

    public static class InterviewRoundHistory {
        private Integer roundNumber;
        private String roundName;
        private String status;
        private String feedback;
        private LocalDateTime interviewDateTime;
        private List<String> interviewers;

        // Getters and setters
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

        public LocalDateTime getInterviewDateTime() {
            return interviewDateTime;
        }

        public void setInterviewDateTime(LocalDateTime interviewDateTime) {
            this.interviewDateTime = interviewDateTime;
        }

        public List<String> getInterviewers() {
            return interviewers;
        }

        public void setInterviewers(List<String> interviewers) {
            this.interviewers = interviewers;
        }
    }

    // Getters and setters
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

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public List<InterviewRoundHistory> getInterviewHistory() {
        return interviewHistory;
    }

    public void setInterviewHistory(List<InterviewRoundHistory> interviewHistory) {
        this.interviewHistory = interviewHistory;
    }
}
