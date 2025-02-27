package org.shad.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for evidence operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceResponse {

    private String encodedEvidence;
    private String evidenceType;
    private String nextStepHint;
}
