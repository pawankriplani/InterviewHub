package com.example.interview_hub.controller;

import com.example.interview_hub.model.dto.ApiResponse;
import com.example.interview_hub.model.dto.CandidateRequest;
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
    public ResponseEntity<ApiResponse> createCandidates(@RequestBody CandidateRequest candidateRequest) {
        boolean success = candidateService.createCandidates(candidateRequest);
        if (success) {
            return new ResponseEntity<>(new ApiResponse("Candidates shortlisted successfully"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponse("Failed to shortlist candidates"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
