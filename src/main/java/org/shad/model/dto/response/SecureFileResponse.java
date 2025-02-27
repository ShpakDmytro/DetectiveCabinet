package org.shad.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for secure file access.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecureFileResponse {

    private String fileContent;
    private String caseResolution;
    private String congratulationsMessage;
}