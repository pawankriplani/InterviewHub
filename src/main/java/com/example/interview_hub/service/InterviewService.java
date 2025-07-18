package com.example.interview_hub.service;

import com.example.interview_hub.model.dto.*;
import com.example.interview_hub.model.entity.*;
import com.example.interview_hub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InterviewService {

    private static final Logger logger = LoggerFactory.getLogger(InterviewService.class);

    private final InterviewRoundRepository interviewRoundRepository;
    private final UserRepository userRepository;
    private final CandidateInterviewRepository candidateInterviewRepository;
    private final CandidateRepository candidateRepository;
    private final CandidateInterviewerRepository candidateInterviewerRepository;

    @Autowired
    public InterviewService(
            InterviewRoundRepository interviewRoundRepository,
            UserRepository userRepository,
            CandidateInterviewRepository candidateInterviewRepository,
            CandidateRepository candidateRepository,
            CandidateInterviewerRepository candidateInterviewerRepository) {
        this.interviewRoundRepository = interviewRoundRepository;
        this.userRepository = userRepository;
        this.candidateInterviewRepository = candidateInterviewRepository;
        this.candidateRepository = candidateRepository;
        this.candidateInterviewerRepository = candidateInterviewerRepository;
    }

    @Transactional
    public List<InterviewRoundResponse> getAllInterviewRounds() {
        List<InterviewRound> rounds = interviewRoundRepository.findAll();
        
        // First, get each candidate's latest interview based on highest round ID
        Map<Integer, CandidateInterview> latestInterviews = rounds.stream()
                .flatMap(round -> round.getCandidateInterviews().stream())
                .collect(Collectors.groupingBy(
                        interview -> interview.getCandidate().getCandidateId(),
                        Collectors.collectingAndThen(
                                Collectors.maxBy((i1, i2) -> i1.getRound().getRoundId().compareTo(i2.getRound().getRoundId())),
                                optionalInterview -> optionalInterview.orElse(null)
                        )
                ));

        // Create a map of all interviews for each candidate
        Map<Integer, List<CandidateInterview>> allCandidateInterviews = rounds.stream()
                .flatMap(round -> round.getCandidateInterviews().stream())
                .collect(Collectors.groupingBy(
                        interview -> interview.getCandidate().getCandidateId()
                ));
        
        // Create responses only including candidates in their latest round
        return rounds.stream()
                .map(round -> {
                    InterviewRoundResponse response = new InterviewRoundResponse();
                    response.setRoundId(round.getRoundId());
                    response.setRoundName(round.getRoundName());
                    
                    // Only include candidates whose latest round is this round
                    List<CandidateResponse> candidates = latestInterviews.values().stream()
                            .filter(interview -> interview != null && interview.getRound().getRoundId().equals(round.getRoundId()))
                            .map(interview -> mapToCandidateResponse(interview, allCandidateInterviews.get(interview.getCandidate().getCandidateId())))
                            .collect(Collectors.toList());
                    
                    response.setCandidates(candidates);
                    return response;
                })
                .collect(Collectors.toList());
    }

    private CandidateResponse mapToCandidateResponse(CandidateInterview interview, List<CandidateInterview> candidateInterviews) {
        CandidateResponse response = new CandidateResponse();
        response.setCandidateId(interview.getCandidate().getCandidateId());
        response.setName(interview.getCandidate().getName());
        response.setStatus(interview.getStatus().toString());
        response.setFeedback(interview.getFeedback());
        response.setJobDetails(interview.getCandidate().getJobDetails());
        response.setInterviewers(mapToInterviewerEmails(interview.getCandidateInterviewers()));
        response.setInterviewDateTime(interview.getScheduledAt());

        // Add manager information
        User manager = interview.getCandidate().getManager();
        if (manager != null) {
            ManagerResponse managerResponse = new ManagerResponse();
            managerResponse.setUserId(manager.getUserId());
            managerResponse.setFullName(manager.getFullName());
            managerResponse.setEmail(manager.getEmail());
            managerResponse.setEmployeeId(manager.getEmployeeId());
            response.setManager(managerResponse);
        }

        // Add interview history
        List<CandidateResponse.InterviewRoundHistory> history = candidateInterviews.stream()
                .sorted((i1, i2) -> i1.getRound().getRoundId().compareTo(i2.getRound().getRoundId()))
                .map(i -> {
                    CandidateResponse.InterviewRoundHistory roundHistory = new CandidateResponse.InterviewRoundHistory();
                    roundHistory.setRoundNumber(i.getRound().getRoundId());
                    roundHistory.setRoundName(i.getRound().getRoundName());
                    roundHistory.setStatus(i.getStatus().toString());
                    roundHistory.setFeedback(i.getFeedback());
                    return roundHistory;
                })
                .collect(Collectors.toList());

        response.setInterviewHistory(history);
        
        // Add score from candidate
        response.setScore(interview.getCandidate().getScore());
        
        return response;
    }

    private List<String> mapToInterviewerEmails(Set<CandidateInterviewer> candidateInterviewers) {
        return candidateInterviewers.stream()
                .map(interviewer -> interviewer.getInterviewer().getEmail())
                .collect(Collectors.toList());
    }

    @Transactional
    public ApiResponse updateInterviewStatus(UpdateInterviewRequest request) {
        try {
            logger.info("Updating interview status for candidate {} and round {}", request.getCandidateId(), request.getRoundId());

            // Find or create CandidateInterview
            CandidateInterview interview = candidateInterviewRepository
                    .findByCandidateCandidateIdAndRoundRoundId(request.getCandidateId(), request.getRoundId())
                    .orElseGet(() -> {
                        CandidateInterview newInterview = new CandidateInterview();
                        
                        // Set candidate
                        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                                .orElseThrow(() -> new RuntimeException("Candidate not found"));
                        newInterview.setCandidate(candidate);
                        
                        // Set round
                        InterviewRound round = interviewRoundRepository.findById(request.getRoundId())
                                .orElseThrow(() -> new RuntimeException("Interview round not found"));
                        newInterview.setRound(round);
                        
                        return newInterview;
                    });

            // Update interview details
            interview.setStatus(CandidateInterview.InterviewStatus.IN_PROGRESS);
            interview.setScheduledAt(request.getEndMeetingTimeStamp());
            interview.setMeetingLink(request.getMeetingLink());

            // Save the interview
            interview = candidateInterviewRepository.save(interview);
            logger.info("Interview updated successfully");

            // Handle interviewer assignments
            if (request.getInterviewerIds() != null && !request.getInterviewerIds().isEmpty()) {
                // Remove existing interviewer assignments
                candidateInterviewerRepository.deleteByCandidateInterviewCandidateInterviewId(interview.getCandidateInterviewId());
                logger.info("Existing interviewer assignments removed");

                // Create new interviewer assignments
                for (Integer interviewerId : request.getInterviewerIds()) {
                    CandidateInterviewer candidateInterviewer = new CandidateInterviewer();
                    candidateInterviewer.setCandidateInterview(interview);
                    
                    // Set reference to existing interviewer
                    Interviewer interviewer = new Interviewer();
                    interviewer.setInterviewerId(interviewerId);
                    candidateInterviewer.setInterviewer(interviewer);
                    
                    candidateInterviewerRepository.save(candidateInterviewer);
                }
                logger.info("New interviewer assignments created");
            }

            // Create response
            return new ApiResponse("Interview status updated successfully");
        } catch (Exception e) {
            logger.error("Error updating interview status", e);
            return new ApiResponse("Error updating interview status: " + e.getMessage());
        }
    }
}
