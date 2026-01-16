package com.example.interview_hub.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Convert(converter = InterviewStatusConverter.class)
    @Column(columnDefinition = "ENUM('Pending', 'In progress', 'Completed', 'Selected', 'Rejected') DEFAULT 'Pending'")
    private InterviewStatus status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "candidateInterview")
    private Set<CandidateInterviewer> candidateInterviewers;

    public enum InterviewStatus {
        PENDING("Pending"),
        IN_PROGRESS("In progress"),
        COMPLETED("Completed"),
        SELECTED("Selected"),
        REJECTED("Rejected");

        private final String value;

        InterviewStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static InterviewStatus fromValue(String value) {
            for (InterviewStatus status : InterviewStatus.values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown status: " + value);
        }
    }

    @Converter
    public static class InterviewStatusConverter implements AttributeConverter<InterviewStatus, String> {
        @Override
        public String convertToDatabaseColumn(InterviewStatus status) {
            return status != null ? status.getValue() : null;
        }

        @Override
        public InterviewStatus convertToEntityAttribute(String value) {
            return value != null ? InterviewStatus.fromValue(value) : null;
        }
    }

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

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
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

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<CandidateInterviewer> getCandidateInterviewers() {
        return candidateInterviewers;
    }

    public void setCandidateInterviewers(Set<CandidateInterviewer> candidateInterviewers) {
        this.candidateInterviewers = candidateInterviewers;
    }
}
