package com.example.interview_hub.model.dto;

import java.util.List;

public class ShortlistRequest {
    private Integer managerId;
    private JobDescriptionRequest jobDescription;
    private List<CandidateRequest> candidates;

    // Getters and Setters
    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public JobDescriptionRequest getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(JobDescriptionRequest jobDescription) {
        this.jobDescription = jobDescription;
    }

    public List<CandidateRequest> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateRequest> candidates) {
        this.candidates = candidates;
    }
}
