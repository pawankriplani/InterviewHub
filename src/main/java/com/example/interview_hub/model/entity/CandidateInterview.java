package com.example.interview_hub.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidate_interviews")
public class CandidateInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer candidateInterviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private InterviewRound round;

    @OneToMany(mappedBy = "candidateInterview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidateInterviewer> interviewers = new ArrayList<>();


    @Column(name = "start_meeting_ts")
    private LocalDateTime startMeetingTs;

    @Column(name = "end_meeting_ts")
    private LocalDateTime endMeetingTs;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "status", columnDefinition = "VARCHAR(20) CHECK (status IN ('Pending', 'In progress', 'Completed', 'Selected', 'Rejected')) DEFAULT 'Pending'")
    private String status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Integer getCandidateInterviewId() {
        return candidateInterviewId;
    }

    public void setCandidateInterviewId(Integer candidateInterviewId) {
        this.candidateInterviewId = candidateInterviewId;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public InterviewRound getRound() {
        return round;
    }

    public void setRound(InterviewRound round) {
        this.round = round;
    }

    public List<CandidateInterviewer> getInterviewers() {
        return interviewers;
    }

    public void setInterviewers(List<CandidateInterviewer> interviewers) {
        this.interviewers = interviewers;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
