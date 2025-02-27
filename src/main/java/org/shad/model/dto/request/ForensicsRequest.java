package org.shad.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for forensics analysis.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForensicsRequest {

    private String sampleData;
    private String analysisType;
    private Long timestamp;
}