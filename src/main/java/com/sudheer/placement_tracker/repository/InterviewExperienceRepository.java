package com.sudheer.placement_tracker.repository;

import com.sudheer.placement_tracker.model.InterviewExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterviewExperienceRepository extends JpaRepository<InterviewExperience, Long> {
    List<InterviewExperience> findByApprovedTrue();
    List<InterviewExperience> findByApprovedFalse();
    List<InterviewExperience> findByCompanyIgnoreCaseAndApprovedTrue(String company);
    List<InterviewExperience> findByUserId(Long userId);
}