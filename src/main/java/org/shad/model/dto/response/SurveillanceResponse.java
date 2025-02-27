package org.shad.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for surveillance operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveillanceResponse {

    private String observationData;
    private String locationDetails;
    private String suspiciousActivity;
    private String nextStepHint;
    private Integer remainingAttempts;
}