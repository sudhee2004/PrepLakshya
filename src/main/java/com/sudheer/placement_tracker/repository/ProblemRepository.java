package com.sudheer.placement_tracker.repository;

import com.sudheer.placement_tracker.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProblemRepository
        extends JpaRepository<Problem, Long> {

    List<Problem> findBySectionId(Long sectionId);
}