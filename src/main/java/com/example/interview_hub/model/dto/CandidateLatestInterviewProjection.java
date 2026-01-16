package com.example.interview_hub.model.dto;

public interface CandidateLatestInterviewProjection {
    Integer getCandidateId();
    String getCandidateName();
    Integer getRoundId();
    String getRoundName();
    String getFeedback();
    String getStatus();
}
