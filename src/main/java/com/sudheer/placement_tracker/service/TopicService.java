package com.sudheer.placement_tracker.service;

import com.sudheer.placement_tracker.model.Topic;
import com.sudheer.placement_tracker.model.User;
import com.sudheer.placement_tracker.repository.TopicRepository;
import com.sudheer.placement_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    public Topic addTopic(Long userId, String name, String category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Topic topic = new Topic();
        topic.setName(name);
        topic.setCategory(category);
        topic.setUser(user);
        topic.setCompleted(false);
        return topicRepository.save(topic);
    }

    public Topic markCompleted(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found!"));
        topic.setCompleted(true);
        return topicRepository.save(topic);
    }

    public List<Topic> getUserTopics(Long userId) {
        return topicRepository.findByUserId(userId);
    }

    public int getProgressPercentage(Long userId) {
        List<Topic> all = topicRepository.findByUserId(userId);
        if (all.isEmpty()) return 0;
        List<Topic> completed = topicRepository.findByUserIdAndCompleted(userId, true);
        return (completed.size() * 100) / all.size();
    }
}