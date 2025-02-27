package org.shad.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for forensics operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForensicsResponse {

    private String analysisResult;
    private String forensicReport;
    private String nextStepHint;
}