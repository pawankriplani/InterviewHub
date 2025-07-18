package com.example.interview_hub.service;

import com.example.interview_hub.model.dto.CandidateRequest;
import com.example.interview_hub.model.dto.CandidateResponse;
import com.example.interview_hub.model.entity.Candidate;
import com.example.interview_hub.model.entity.CandidateInterview;
import com.example.interview_hub.model.entity.InterviewRound;
import com.example.interview_hub.model.entity.User;
import com.example.interview_hub.repository.CandidateInterviewRepository;
import com.example.interview_hub.repository.CandidateRepository;
import com.example.interview_hub.repository.InterviewRoundRepository;
import com.example.interview_hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final InterviewRoundRepository interviewRoundRepository;
    private final CandidateInterviewRepository candidateInterviewRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository, 
                          UserRepository userRepository,
                          InterviewRoundRepository interviewRoundRepository,
                          CandidateInterviewRepository candidateInterviewRepository) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.interviewRoundRepository = interviewRoundRepository;
        this.candidateInterviewRepository = candidateInterviewRepository;
    }

@Transactional
    public boolean createCandidates(CandidateRequest candidateRequest) {
        try {
            User manager = userRepository.findById(candidateRequest.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found"));

            // Find Round 1 once for all candidates
            InterviewRound round1 = interviewRoundRepository.findById(1)
                    .orElseThrow(() -> new EntityNotFoundException("Round 1 not found"));

            for (CandidateRequest.CandidateData candidateData : candidateRequest.getCandidates()) {
                Candidate candidate = new Candidate();
                candidate.setName(candidateData.getName());
                candidate.setEmail(candidateData.getEmail());
                candidate.setPhone(candidateData.getPhone());
                candidate.setPositionApplied(candidateData.getPositionApplied());
                candidate.setJobDetails(candidateData.getJobDetails());
                candidate.setManager(manager);
                candidate.setScore(candidateData.getScore());
                candidate.setResumeId(candidateData.getResumeId());
                candidate.setEvaluationId(candidateData.getEvaluationId());

                Candidate savedCandidate = candidateRepository.save(candidate);

                // Create CandidateInterview for Round 1
                CandidateInterview candidateInterview = new CandidateInterview();
                candidateInterview.setCandidate(savedCandidate);
                candidateInterview.setRound(round1);
                candidateInterview.setStatus("Pending");
                candidateInterview.setCreatedAt(java.time.LocalDateTime.now());

                candidateInterviewRepository.save(candidateInterview);
            }

            return true;
        } catch (Exception e) {
            // Log the exception if needed
            return false;
        }
    }
}
