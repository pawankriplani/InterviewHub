package com.example.interview_hub.controller;

import com.example.interview_hub.model.dto.InterviewRoundResponse;
import com.example.interview_hub.model.dto.UpdateInterviewRequest;
import com.example.interview_hub.model.dto.UpdateStatusResponse;
import com.example.interview_hub.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class InterviewController {

    private final InterviewService interviewService;

    @Autowired
    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping("/interview-rounds")
    public ResponseEntity<Map<String, List<InterviewRoundResponse>>> getInterviewRounds() {
        List<InterviewRoundResponse> rounds = interviewService.getAllInterviewRounds();
        Map<String, List<InterviewRoundResponse>> response = new HashMap<>();
        response.put("list", rounds);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/interviews/update-status")
    public ResponseEntity<UpdateStatusResponse> updateInterviewStatus(@RequestBody UpdateInterviewRequest request) {
        UpdateStatusResponse response = interviewService.updateInterviewStatus(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
