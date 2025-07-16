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
}
