package com.example.interview_hub.service;

import com.example.interview_hub.model.dto.CandidateRequest;
import com.example.interview_hub.model.dto.ShortlistRequest;
import com.example.interview_hub.model.dto.JobDescriptionRequest;
import com.example.interview_hub.model.dto.CandidateLatestInterviewDTO;
import com.example.interview_hub.model.dto.CandidateLatestInterviewProjection;
import com.example.interview_hub.model.entity.Candidate;
import com.example.interview_hub.model.entity.CandidateInterview;
import com.example.interview_hub.model.entity.InterviewRound;
import com.example.interview_hub.model.entity.User;
import com.example.interview_hub.model.entity.JobDescription;
import com.example.interview_hub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final InterviewRoundRepository interviewRoundRepository;
    private final CandidateInterviewRepository candidateInterviewRepository;
    private final JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository, 
                            UserRepository userRepository,
                            InterviewRoundRepository interviewRoundRepository,
                            CandidateInterviewRepository candidateInterviewRepository,
                            JobDescriptionRepository jobDescriptionRepository) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.interviewRoundRepository = interviewRoundRepository;
        this.candidateInterviewRepository = candidateInterviewRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
    }

    @Transactional
    public boolean shortlistCandidates(ShortlistRequest shortlistRequest) {
        try {
            User manager = userRepository.findById(shortlistRequest.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found"));

            // Create and save JobDescription
            JobDescription jobDescription = createJobDescription(shortlistRequest.getJobDescription());
            JobDescription savedJobDescription = jobDescriptionRepository.save(jobDescription);

            // Find Round 1 once for all candidates
            InterviewRound round1 = interviewRoundRepository.findById(1)
                    .orElseThrow(() -> new EntityNotFoundException("Round 1 not found"));

            for (CandidateRequest candidateRequest : shortlistRequest.getCandidates()) {
                Candidate candidate = new Candidate();
                candidate.setName(candidateRequest.getName());
                candidate.setEmail(candidateRequest.getEmail());
                candidate.setPhone(candidateRequest.getPhone());
                candidate.setPositionApplied(candidateRequest.getPositionApplied());
                candidate.setJobDetails(candidateRequest.getJobDetails());
                candidate.setManager(manager);
                candidate.setScore(candidateRequest.getScore());
                candidate.setResumeId(candidateRequest.getResumeId());
                candidate.setEvaluationId(candidateRequest.getEvaluationId());
                candidate.setJobDescription(savedJobDescription);

                Candidate savedCandidate = candidateRepository.save(candidate);

                // Create CandidateInterview for Round 1
                CandidateInterview candidateInterview = new CandidateInterview();
                candidateInterview.setCandidate(savedCandidate);
                candidateInterview.setRound(round1);
                candidateInterview.setStatus("Pending");
                candidateInterview.setCreatedAt(LocalDateTime.now());

                candidateInterviewRepository.save(candidateInterview);
            }

            return true;
        } catch (Exception e) {
            // Log the exception if needed
            return false;
        }
    }

    private JobDescription createJobDescription(JobDescriptionRequest request) {
        JobDescription jobDescription = new JobDescription();
        jobDescription.setTitle(request.getTitle());
        jobDescription.setLocation(request.getLocation());
        jobDescription.setCompany(request.getCompany());
        jobDescription.setOverview(request.getOverview());
        jobDescription.setSummary(request.getSummary());
        jobDescription.setResponsibilities(request.getResponsibilities());
        jobDescription.setRequiredQualifications(request.getRequiredQualifications());
        jobDescription.setPreferredQualifications(request.getPreferredQualifications());
        jobDescription.setBenefits(request.getBenefits());
        jobDescription.setTechnicalSkills(request.getTechnicalSkills());
        jobDescription.setCreatedAt(LocalDateTime.now());
        jobDescription.setUpdatedAt(LocalDateTime.now());
        return jobDescription;
    }

    public List<CandidateLatestInterviewDTO> getLatestInterviewsForAllCandidates() {
        List<CandidateLatestInterviewProjection> projections = candidateInterviewRepository.findLatestInterviewsForAllCandidates();
        return projections.stream()
            .map(this::convertToDTO)
            .toList();
    }

    public List<CandidateLatestInterviewDTO> getLatestInterviewsByManagerId(Integer managerId) {
        List<CandidateLatestInterviewProjection> projections = candidateInterviewRepository.findLatestInterviewsByManagerId(managerId);
        return projections.stream()
            .map(this::convertToDTO)
            .toList();
    }

    private CandidateLatestInterviewDTO convertToDTO(CandidateLatestInterviewProjection projection) {
        return new CandidateLatestInterviewDTO(
            projection.getCandidateId(),
            projection.getCandidateName(),
            projection.getRoundId(),
            projection.getRoundName(),
            projection.getFeedback(),
            projection.getStatus()
        );
    }
}
