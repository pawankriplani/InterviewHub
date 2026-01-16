package com.example.interview_hub.model.dto;

import java.time.LocalDateTime;
import java.util.List;


public class UpdateInterviewRequest {
    private Integer candidateId;
    private Integer roundId;
    private List<Interviewer> interviewers;
    private String status;
    private String meetingLink;
    private LocalDateTime startMeetingTimeStamp;
    private LocalDateTime endMeetingTimeStamp;
    private String feedback;

    // Getters and Setters
    // Note: scheduledAt getter and setter have been removed
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

    public List<Interviewer> getInterviewers() {
        return interviewers;
    }

    public void setInterviewers(List<Interviewer> interviewers) {
        this.interviewers = interviewers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public LocalDateTime getStartMeetingTimeStamp() {
        return startMeetingTimeStamp;
    }

    public void setStartMeetingTimeStamp(LocalDateTime startMeetingTimeStamp) {
        this.startMeetingTimeStamp = startMeetingTimeStamp;
    }

    public LocalDateTime getEndMeetingTimeStamp() {
        return endMeetingTimeStamp;
    }

    public void setEndMeetingTimeStamp(LocalDateTime endMeetingTimeStamp) {
        this.endMeetingTimeStamp = endMeetingTimeStamp;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
