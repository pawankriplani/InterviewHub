package com.example.interview_hub.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateInterviewRequest {
    private Integer candidateId;
    private Integer roundId;
    private List<Integer> interviewerIds;
    private String status;
    private String email;
    private String meetingLink;
    private LocalDateTime endMeetingTimeStamp;

    // Getters and Setters
    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public List<Integer> getInterviewerIds() {
        return interviewerIds;
    }

    public void setInterviewerIds(List<Integer> interviewerIds) {
        this.interviewerIds = interviewerIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public LocalDateTime getEndMeetingTimeStamp() {
        return endMeetingTimeStamp;
    }

    public void setEndMeetingTimeStamp(LocalDateTime endMeetingTimeStamp) {
        this.endMeetingTimeStamp = endMeetingTimeStamp;
    }
}
