package com.example.interview_hub.repository;

import com.example.interview_hub.model.entity.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDescriptionRepository extends JpaRepository<JobDescription, Integer> {
    // Add custom query methods if needed
}
