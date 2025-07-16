package com.example.interview_hub.service;

import com.example.interview_hub.model.dto.CandidateResponse;
import com.example.interview_hub.model.dto.InterviewRoundResponse;
import com.example.interview_hub.model.dto.ManagerResponse;
import com.example.interview_hub.model.entity.CandidateInterview;
import com.example.interview_hub.model.entity.CandidateInterviewer;
import com.example.interview_hub.model.entity.InterviewRound;
import com.example.interview_hub.model.entity.User;
import com.example.interview_hub.repository.InterviewRoundRepository;
import com.example.interview_hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InterviewService {

    private final InterviewRoundRepository interviewRoundRepository;
    private final UserRepository userRepository;

    @Autowired
    public InterviewService(InterviewRoundRepository interviewRoundRepository, UserRepository userRepository) {
        this.interviewRoundRepository = interviewRoundRepository;
        this.userRepository = userRepository;
    }

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
        
        // Create responses only including candidates in their latest round
        return rounds.stream()
                .map(round -> {
                    InterviewRoundResponse response = new InterviewRoundResponse();
                    response.setRoundId(round.getRoundId());
                    response.setRoundName(round.getRoundName());
                    
                    // Only include candidates whose latest round is this round
                    List<CandidateResponse> candidates = latestInterviews.values().stream()
                            .filter(interview -> interview != null && interview.getRound().getRoundId().equals(round.getRoundId()))
                            .map(this::mapToCandidateResponse)
                            .collect(Collectors.toList());
                    
                    response.setCandidates(candidates);
                    return response;
                })
                .collect(Collectors.toList());
    }

    private CandidateResponse mapToCandidateResponse(CandidateInterview interview) {
        CandidateResponse response = new CandidateResponse();
        response.setCandidateId(interview.getCandidate().getCandidateId());
        response.setName(interview.getCandidate().getName());
        response.setStatus(interview.getStatus().toString());
        response.setFeedback(interview.getFeedback());
        response.setJobDetails(interview.getCandidate().getJobDetails());
        response.setInterviewers(mapToInterviewerEmails(interview.getCandidateInterviewers()));
        response.setInterviewDateTime(interview.getScheduledAt());

        
        // Add manager information
        Integer managerId = interview.getCandidate().getManager().getUserId();
        User manager = userRepository.findById(managerId).orElse(null);
        if (manager != null) {
            ManagerResponse managerResponse = new ManagerResponse();
            managerResponse.setUserId(manager.getUserId());
            managerResponse.setFullName(manager.getFullName());
            managerResponse.setEmail(manager.getEmail());
            managerResponse.setEmployeeId(manager.getEmployeeId());
            response.setManager(managerResponse);
        }
        
        return response;
    }

    private List<String> mapToInterviewerEmails(Set<CandidateInterviewer> candidateInterviewers) {
        return candidateInterviewers.stream()
                .map(interviewer -> interviewer.getInterviewer().getEmail())
                .collect(Collectors.toList());
    }
}
