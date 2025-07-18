package com.example.interview_hub.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CandidateResponse {
    private Integer candidateId;
    private String name;
    private String status;
    private String feedback;
    private String jobDetails;
    private Integer interviewerId;
    private String interviewerEmail;
    private LocalDateTime scheduledAt;
    private LocalDateTime startMeetingTs;
    private LocalDateTime endMeetingTs;
    private String meetingLink;
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

    public Integer getInterviewerId() {
        return interviewerId;
    }

    public void setInterviewerId(Integer interviewerId) {
        this.interviewerId = interviewerId;
    }

    public String getInterviewerEmail() {
        return interviewerEmail;
    }

    public void setInterviewerEmail(String interviewerEmail) {
        this.interviewerEmail = interviewerEmail;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public LocalDateTime getStartMeetingTs() {
        return startMeetingTs;
    }

    public void setStartMeetingTs(LocalDateTime startMeetingTs) {
        this.startMeetingTs = startMeetingTs;
    }

    public LocalDateTime getEndMeetingTs() {
        return endMeetingTs;
    }

    public void setEndMeetingTs(LocalDateTime endMeetingTs) {
        this.endMeetingTs = endMeetingTs;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
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
