package org.shad.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for the initial tip.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipResponse {
    private String tipMessage;
    private String nextStepHint;
    private String registrationEndpoint;
}
