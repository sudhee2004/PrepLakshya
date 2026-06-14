package com.sudheer.placement_tracker.service;

import com.sudheer.placement_tracker.model.*;
import com.sudheer.placement_tracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserProgressRepository userProgressRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all DSA sections
    public List<Section> getDSASections() {
        return sectionRepository.findByCategory("DSA");
    }

    // Get problems by section
    public List<Problem> getProblemsBySection(Long sectionId, Long userId) {
        List<Problem> problems = problemRepository.findBySectionId(sectionId);
        List<UserProgress> progressList = userProgressRepository.findByUserId(userId);

        for (Problem problem : problems) {
            for (UserProgress progress : progressList) {
                if (progress.getProblem().getId().equals(problem.getId())) {
                    problem.setUserStatus(progress.getStatus().toString());
                    break;
                }
            }
        }
        return problems;
    }

    // Update problem status
    public UserProgress updateStatus(Long userId,
                                     Long problemId,
                                     String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new RuntimeException("Problem not found"));

        // Check if progress entry already exists
        UserProgress progress = userProgressRepository
                .findByUserIdAndProblemId(userId, problemId)
                .orElse(new UserProgress());

        progress.setUser(user);
        progress.setProblem(problem);
        progress.setStatus(
                UserProgress.Status.valueOf(status));

        return userProgressRepository.save(progress);
    }

    // Get section progress % for a user
    public int getSectionProgress(Long userId, Long sectionId) {
        List<Problem> problems =
                problemRepository.findBySectionId(sectionId);

        if (problems.isEmpty()) return 0;

        List<UserProgress> progressList =
                userProgressRepository.findByUserId(userId);

        long solved = progressList.stream()
                .filter(p -> p.getProblem()
                        .getSection().getId().equals(sectionId)
                        && p.getStatus() == UserProgress.Status.SOLVED)
                .count();

        return (int) ((solved * 100) / problems.size());
    }
}