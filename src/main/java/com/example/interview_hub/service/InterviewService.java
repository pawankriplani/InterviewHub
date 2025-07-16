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
        return rounds.stream().map(this::mapToInterviewRoundResponse).collect(Collectors.toList());
    }

    private InterviewRoundResponse mapToInterviewRoundResponse(InterviewRound round) {
        InterviewRoundResponse response = new InterviewRoundResponse();
        response.setRoundId(round.getRoundId());
        response.setRoundName(round.getRoundName());
        response.setCandidates(mapToCandidateResponses(round.getCandidateInterviews()));
        return response;
    }

    private List<CandidateResponse> mapToCandidateResponses(Set<CandidateInterview> candidateInterviews) {
        return candidateInterviews.stream()
                .map(this::mapToCandidateResponse)
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
