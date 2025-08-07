package com.example.interview_hub.service;

import com.example.interview_hub.model.dto.*;
import com.example.interview_hub.model.entity.*;
import com.example.interview_hub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.interview_hub.model.dto.Interviewer;

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
        Integer lastRoundId = interviewRoundRepository.findMaxRoundId();
        
        // Calculate 15 days ago from today's date in Asia/Kolkata timezone
        ZonedDateTime kolkataToday = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        LocalDateTime fifteenDaysAgo = kolkataToday.minusDays(15).toLocalDateTime();
        
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
                            .filter(interview -> shouldIncludeInterview(interview, fifteenDaysAgo, lastRoundId))
                            .map(interview -> mapToCandidateResponse(interview, allCandidateInterviews.get(interview.getCandidate().getCandidateId())))
                            .collect(Collectors.toList());
                    
                    response.setCandidates(candidates);
                    return response;
                })
                .collect(Collectors.toList());
    }

    private boolean shouldIncludeInterview(CandidateInterview interview, LocalDateTime fifteenDaysAgo, Integer lastRoundId) {
        if (!"Selected".equals(interview.getStatus()) && !"Rejected".equals(interview.getStatus())) {
            return true; // Include all non-Selected and non-Rejected interviews
        }
        
        LocalDateTime updatedAt = interview.getUpdatedAt();
        if (updatedAt == null) {
            return false; // If no updated_at timestamp, don't include
        }
        
        if ("Selected".equals(interview.getStatus())) {
            boolean isLastRound = lastRoundId != null && 
                                lastRoundId.equals(interview.getRound().getRoundId());
            
            if (isLastRound) {
                // For Selected status in last round, apply 15-day filter
                return updatedAt.isAfter(fifteenDaysAgo);
            } else {
                // For Selected status in any round below last round, always include
                return true;
            }
        }
        
        // For Rejected status, just check if within 15 days
        return updatedAt.isAfter(fifteenDaysAgo);
    }

    private CandidateResponse mapToCandidateResponse(CandidateInterview interview, List<CandidateInterview> candidateInterviews) {
        CandidateResponse response = new CandidateResponse();
        response.setCandidateId(interview.getCandidate().getCandidateId());
        response.setName(interview.getCandidate().getName());
        response.setEmail(interview.getCandidate().getEmail());
        response.setStatus(interview.getStatus());
        response.setFeedback(interview.getFeedback());
        response.setJobDetails(interview.getCandidate().getJobDetails());
        response.setResumeId(interview.getCandidate().getResumeId());
        response.setJobDescription(interview.getCandidate().getJobDescription());
        
        // Get interviewers from CandidateInterviewer
        List<CandidateInterviewer> candidateInterviewers = candidateInterviewerRepository.findByCandidateInterviewCandidateInterviewId(interview.getCandidateInterviewId());
        List<Interviewer> interviewers = candidateInterviewers.stream()
            .map(ci -> {
                Interviewer interviewer = new Interviewer();
                interviewer.setInterviewerId(ci.getInterviewerId());
                interviewer.setInterviewerEmail(ci.getInterviewerEmail());
                return interviewer;
            })
            .collect(Collectors.toList());
        response.setInterviewers(interviewers);
        
        response.setStartMeetingTs(interview.getStartMeetingTs());
        response.setEndMeetingTs(interview.getEndMeetingTs());
        response.setMeetingLink(interview.getMeetingLink());

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
                    roundHistory.setStatus(i.getStatus());
                    roundHistory.setFeedback(i.getFeedback());
                    return roundHistory;
                })
                .collect(Collectors.toList());

        response.setInterviewHistory(history);
        
        // Add score from candidate
        response.setScore(interview.getCandidate().getScore());
        
        return response;
    }

    @Scheduled(cron = "0 */30 * * * *")  // Run every 30 minutes
    @Transactional
    public void updateCompletedInterviews() { 	
    	logger.info("Starting scheduled task to update completed interviews");
    	ZonedDateTime kolkataNow = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
    	LocalDateTime currentTime = kolkataNow.toLocalDateTime();
    	candidateInterviewRepository.updateCompletedInterviews(currentTime);
    	logger.info("Finished updating completed interviews at time: {}", currentTime);
    }

    @Transactional
    public ApiResponse updateInterviewStatus(UpdateInterviewRequest request) {
        try {
            logger.info("Updating interview status for candidate {} and round {}", request.getCandidateId(), request.getRoundId());

            // Check for existing round 1 interview with Pending status when moving to higher round
            if (request.getRoundId() > 1) {
                Optional<CandidateInterview> round1Interview = candidateInterviewRepository
                    .findByCandidateCandidateIdAndRoundRoundIdAndStatus(request.getCandidateId(), 1, "Pending");
                
                if (round1Interview.isPresent()) {
                    // Update round 1 interview status to Selected
                    CandidateInterview interview = round1Interview.get();
                    interview.setStatus("Selected");
                    candidateInterviewRepository.save(interview);
                    logger.info("Updated round 1 interview status to Selected for candidate {}", request.getCandidateId());
                }
            }

            // Find or create CandidateInterview
            CandidateInterview interview = findOrCreateInterview(request);

            // Check if status is Selected or Rejected
            if ("Selected".equals(request.getStatus()) || "Rejected".equals(request.getStatus())) {
                // Only update status and feedback
                interview.setStatus(request.getStatus());
                interview.setFeedback(request.getFeedback());
                logger.info("Updated status to {} for candidate {} and round {}", request.getStatus(), request.getCandidateId(), request.getRoundId());
            } else {
                // Update all fields (current behavior)
                interview.setStatus("In progress");
                interview.setMeetingLink(request.getMeetingLink());
                interview.setStartMeetingTs(request.getStartMeetingTimeStamp());
                interview.setEndMeetingTs(request.getEndMeetingTimeStamp());
                interview.setFeedback(request.getFeedback());
                logger.info("Updated all interview details for candidate {} and round {}", request.getCandidateId(), request.getRoundId());
            }

            // Save the interview
            interview = candidateInterviewRepository.save(interview);

            // Update or create CandidateInterviewers only if interviewers are provided
            if (request.getInterviewers() != null && !request.getInterviewers().isEmpty()) {
                List<CandidateInterviewer> existingInterviewers = candidateInterviewerRepository
                    .findByCandidateInterviewCandidateInterviewId(interview.getCandidateInterviewId());

                // Remove existing interviewers not in the new list
                existingInterviewers.removeIf(existing -> 
                    request.getInterviewers().stream()
                        .noneMatch(newInterviewer -> 
                            existing.getInterviewerId().equals(newInterviewer.getInterviewerId())));

                // Update or add new interviewers
                for (Interviewer newInterviewer : request.getInterviewers()) {
                    CandidateInterviewer interviewer = existingInterviewers.stream()
                        .filter(existing -> existing.getInterviewerId().equals(newInterviewer.getInterviewerId()))
                        .findFirst()
                        .orElse(new CandidateInterviewer());

                    interviewer.setCandidateInterview(interview);
                    interviewer.setInterviewerId(newInterviewer.getInterviewerId());
                    interviewer.setInterviewerEmail(newInterviewer.getInterviewerEmail());
                    candidateInterviewerRepository.save(interviewer);
                }

                logger.info("Interview and interviewers details updated successfully");
            } else {
                logger.info("Interview details updated successfully (no interviewers provided)");
            }

            return new ApiResponse("Interview status updated successfully");
        } catch (Exception e) {
            logger.error("Error updating interview status", e);
            return new ApiResponse("Error updating interview status: " + e.getMessage());
        }
    }

    private CandidateInterview findOrCreateInterview(UpdateInterviewRequest request) {
        return candidateInterviewRepository
                .findByCandidateCandidateIdAndRoundRoundId(request.getCandidateId(), request.getRoundId())
                .map(existingInterview -> {
                    // Set updated_at for existing interview
                    existingInterview.setUpdatedAt(LocalDateTime.now());
                    logger.info("Updating existing interview for candidate {} and round {}", request.getCandidateId(), request.getRoundId());
                    return existingInterview;
                })
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
                    
                    // Set creation timestamp and explicitly set updated_at to null for new records
                    newInterview.setCreatedAt(LocalDateTime.now());
                    newInterview.setUpdatedAt(null);
                    
                    logger.info("Creating new interview for candidate {} and round {}", request.getCandidateId(), request.getRoundId());
                    return newInterview;
                });
    }
}
