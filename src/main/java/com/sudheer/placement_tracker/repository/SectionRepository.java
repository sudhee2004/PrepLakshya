package com.sudheer.placement_tracker.repository;

import com.sudheer.placement_tracker.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SectionRepository
        extends JpaRepository<Section, Long> {

    List<Section> findByCategory(String category);
}