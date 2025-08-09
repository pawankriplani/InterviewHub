package com.example.interview_hub.service;

import com.example.interview_hub.model.dto.*;
import com.example.interview_hub.model.entity.*;
import com.example.interview_hub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<CandidateInterviewHistoryResponse> getCandidateInterviewHistoryByManagerId(Integer managerId) {
        List<CandidateInterviewHistoryProjection> projections = candidateInterviewRepository.findInterviewHistoryByManagerId(managerId);
        
        Map<Integer, List<CandidateInterviewHistoryProjection>> groupedByCandidateId = projections.stream()
            .collect(Collectors.groupingBy(CandidateInterviewHistoryProjection::getCandidateId));
        
        return groupedByCandidateId.entrySet().stream()
            .map(entry -> createCandidateResponse(entry.getKey(), entry.getValue()))
            .sorted(Comparator.comparing(CandidateInterviewHistoryResponse::getCandidateId))
            .collect(Collectors.toList());
    }

    private CandidateInterviewHistoryResponse createCandidateResponse(Integer candidateId, List<CandidateInterviewHistoryProjection> projections) {
        if (projections == null || projections.isEmpty()) {
            CandidateInterviewHistoryResponse response = new CandidateInterviewHistoryResponse();
            response.setCandidateId(candidateId);
            response.setInterviewHistory(new ArrayList<>());
            return response;
        }

        CandidateInterviewHistoryResponse response = new CandidateInterviewHistoryResponse();
        response.setCandidateId(candidateId);
        response.setName(projections.get(0).getCandidateName());
        response.setCurrentRound(projections.get(projections.size() - 1).getRoundNumber());
        
        List<CandidateInterviewHistoryResponse.InterviewRoundHistory> history = projections.stream()
            .map(this::createInterviewRoundHistory)
            .sorted(Comparator.comparing(CandidateInterviewHistoryResponse.InterviewRoundHistory::getRoundNumber))
            .collect(Collectors.toList());
        
        response.setInterviewHistory(history);
        return response;
    }

    private CandidateInterviewHistoryResponse.InterviewRoundHistory createInterviewRoundHistory(CandidateInterviewHistoryProjection projection) {
        CandidateInterviewHistoryResponse.InterviewRoundHistory history = new CandidateInterviewHistoryResponse.InterviewRoundHistory();
        history.setRoundNumber(projection.getRoundNumber());
        history.setRoundName(projection.getRoundName());
        history.setStatus(projection.getStatus());
        history.setFeedback(projection.getFeedback());
        history.setInterviewDateTime(projection.getInterviewDateTime());
        
        String interviewers = projection.getInterviewers();
        if (interviewers != null && !interviewers.trim().isEmpty()) {
            history.setInterviewers(Arrays.asList(interviewers.split(", ")));
        } else {
            history.setInterviewers(new ArrayList<>());
        }
        return history;
    }

    @Transactional(readOnly = true)
    public ManagerResponse getCandidateManager(Integer candidateId) {
        Candidate candidate = candidateRepository.findByIdWithManager(candidateId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
    
        User manager = candidate.getManager();
        if (manager == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found for candidate");
        }
    
        ManagerResponse response = new ManagerResponse();
        response.setName(manager.getFullName());
        response.setEmail(manager.getEmail());
        return response;
    }
}
