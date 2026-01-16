package com.example.interview_hub.model.dto;

import java.time.LocalDateTime;

public interface CandidateInterviewHistoryProjection {
    Integer getCandidateId();
    String getCandidateName();
    Integer getInterviewId();
    Integer getRoundNumber();
    String getRoundName();
    String getFeedback();
    String getStatus();
    LocalDateTime getInterviewDateTime();
    String getInterviewers();
}
