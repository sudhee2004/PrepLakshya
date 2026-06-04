package com.sudheer.placement_tracker.repository;

import com.sudheer.placement_tracker.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByUserId(Long userId);
    List<Topic> findByUserIdAndCompleted(Long userId, boolean completed);
}