package com.example.interview_hub.controller;

import com.example.interview_hub.model.dto.ApiResponse;
import com.example.interview_hub.model.dto.ShortlistRequest;
import com.example.interview_hub.model.dto.CandidateLatestInterviewDTO;
import com.example.interview_hub.model.dto.CandidateInterviewHistoryResponse;
import com.example.interview_hub.model.dto.ManagerResponse;
import java.util.List;
import com.example.interview_hub.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("/shortlist")
    public ResponseEntity<ApiResponse> shortlistCandidates(@RequestBody ShortlistRequest shortlistRequest) {
        boolean success = candidateService.shortlistCandidates(shortlistRequest);
        if (success) {
            return new ResponseEntity<>(new ApiResponse("Candidates shortlisted successfully"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponse("Failed to shortlist candidates"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/latest-interviews")
    public ResponseEntity<List<CandidateLatestInterviewDTO>> getLatestInterviewsForAllCandidates() {
        List<CandidateLatestInterviewDTO> latestInterviews = candidateService.getLatestInterviewsForAllCandidates();
        return ResponseEntity.ok(latestInterviews);
    }

    @GetMapping("/latest-interviews/manager/{managerId}")
    public ResponseEntity<List<CandidateLatestInterviewDTO>> getLatestInterviewsByManagerId(@PathVariable Integer managerId) {
        List<CandidateLatestInterviewDTO> latestInterviews = candidateService.getLatestInterviewsByManagerId(managerId);
        return ResponseEntity.ok(latestInterviews);
    }

    @GetMapping("/interview-history")
    public ResponseEntity<List<CandidateInterviewHistoryResponse>> getCandidateInterviewHistory(@RequestParam Integer managerId) {
        List<CandidateInterviewHistoryResponse> interviewHistory = candidateService.getCandidateInterviewHistoryByManagerId(managerId);
        return ResponseEntity.ok(interviewHistory);
    }

    @GetMapping("/{candidateId}/manager")
    public ResponseEntity<ManagerResponse> getCandidateManager(@PathVariable Integer candidateId) {
        ManagerResponse manager = candidateService.getCandidateManager(candidateId);
        return ResponseEntity.ok(manager);
    }
}
