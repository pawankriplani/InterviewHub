package com.example.interview_hub.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class CandidateRequest {
    @NotEmpty(message = "Candidates list cannot be empty")
    private List<CandidateData> candidates;

    public static class CandidateData {
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        private String name;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        private String email;

        @NotBlank(message = "Phone number is required")
        @Size(max = 15, message = "Phone number must not exceed 15 characters")
        private String phone;

        @NotBlank(message = "Position applied is required")
        @Size(max = 100, message = "Position applied must not exceed 100 characters")
        private String positionApplied;

        private String jobDetails;

        @NotNull(message = "Score is required")
        private Double score;

        @NotBlank(message = "Resume ID is required")
        @Size(max = 50, message = "Resume ID must not exceed 50 characters")
        private String resumeId;

        @NotBlank(message = "Evaluation ID is required")
        @Size(max = 50, message = "Evaluation ID must not exceed 50 characters")
        private String evaluationId;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPositionApplied() {
            return positionApplied;
        }

        public void setPositionApplied(String positionApplied) {
            this.positionApplied = positionApplied;
        }

        public String getJobDetails() {
            return jobDetails;
        }

        public void setJobDetails(String jobDetails) {
            this.jobDetails = jobDetails;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public String getResumeId() {
            return resumeId;
        }

        public void setResumeId(String resumeId) {
            this.resumeId = resumeId;
        }

        public String getEvaluationId() {
            return evaluationId;
        }

        public void setEvaluationId(String evaluationId) {
            this.evaluationId = evaluationId;
        }
    }

    @NotNull(message = "Manager ID is required")
    private Integer managerId;

    // Getters and Setters
    public List<CandidateData> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateData> candidates) {
        this.candidates = candidates;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}
