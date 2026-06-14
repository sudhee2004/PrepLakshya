package com.sudheer.placement_tracker.service;

import com.sudheer.placement_tracker.model.*;
import com.sudheer.placement_tracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> getOverallProgress(Long userId) {
        List<Section> sections = sectionRepository.findByCategory("DSA");
        List<UserProgress> progressList = userProgressRepository.findByUserId(userId);

        int totalProblems = 0;
        int totalSolved = 0;
        List<Map<String, Object>> sectionStats = new ArrayList<>();

        for (Section section : sections) {
            List<Problem> problems = problemRepository.findBySectionId(section.getId());
            int sectionTotal = problems.size();
            int sectionSolved = 0;

            for (Problem problem : problems) {
                for (UserProgress progress : progressList) {
                    if (progress.getProblem().getId().equals(problem.getId())
                            && progress.getStatus() == UserProgress.Status.SOLVED) {
                        sectionSolved++;
                        break;
                    }
                }
            }

            int sectionPct = sectionTotal == 0 ? 0 : (sectionSolved * 100) / sectionTotal;

            Map<String, Object> stat = new HashMap<>();
            stat.put("name", section.getName());
            stat.put("total", sectionTotal);
            stat.put("solved", sectionSolved);
            stat.put("percentage", sectionPct);
            sectionStats.add(stat);

            totalProblems += sectionTotal;
            totalSolved += sectionSolved;
        }

        int overallPct = totalProblems == 0 ? 0 : (totalSolved * 100) / totalProblems;

        Map<String, Object> result = new HashMap<>();
        result.put("overallPercentage", overallPct);
        result.put("totalProblems", totalProblems);
        result.put("totalSolved", totalSolved);
        result.put("totalPending", totalProblems - totalSolved);
        result.put("sections", sectionStats);

        return result;
    }
}