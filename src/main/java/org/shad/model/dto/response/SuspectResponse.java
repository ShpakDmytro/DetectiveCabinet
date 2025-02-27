package org.shad.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for suspect operations.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuspectResponse {

    private String suspectStatement;
    private String alibi;
    private String evidenceConnection;
    private String nextStepHint;
}