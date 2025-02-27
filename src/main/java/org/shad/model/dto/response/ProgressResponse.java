package org.shad.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for progress tracking.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgressResponse {

    private String username;
    private int completedChallenges;
    private int totalChallenges;
    private double completionPercentage;
    private LocalDateTime lastActivity;
    private List<ChallengeProgress> challenges;

    /**
     * Inner class representing progress on a specific challenge.
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChallengeProgress {
        private String challengeName;
        private boolean completed;
        private int attempts;
        private LocalDateTime startTime;
        private LocalDateTime completionTime;
        private Long timeSpentSeconds;
    }
}