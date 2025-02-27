package org.shad.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.shad.model.dto.ApiResponse;
import org.shad.model.dto.request.ForensicsRequest;
import org.shad.model.dto.request.RegisterRequest;
import org.shad.model.dto.response.AuthResponse;
import org.shad.model.dto.response.EvidenceResponse;
import org.shad.model.dto.response.ForensicsResponse;
import org.shad.model.dto.response.RegistrationResponse;
import org.shad.model.dto.response.SecureFileResponse;
import org.shad.model.dto.response.SurveillanceResponse;
import org.shad.model.dto.response.SuspectResponse;
import org.shad.model.dto.response.TipResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Implementation of the ApiClientService interface.
 * This class provides methods to interact with the Detective Challenge API.
 */
public class ApiClientServiceImpl implements ApiClientService {

    private static final String BASE_URL = "http://localhost:8080/api/v1";
    private final RestTemplate restTemplate;

    public ApiClientServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Retrieves the initial tip from the external API.
     *
     * <p>This method sends a GET request to the endpoint {@code BASE_URL + "/tip"}
     * and attempts to fetch a {@link TipResponse}. If the response status is 200 (OK)
     * and contains a valid body, the method returns the {@link TipResponse}. Otherwise,
     * it throws an exception indicating a failure.</p>
     *
     * <p>In case of any errors, a {@link RuntimeException} is thrown with a relevant message.</p>
     *
     * @return {@link TipResponse} containing the retrieved tip information.
     * @throws RuntimeException if the API request fails or the response is invalid.
     */
    @Override
    public TipResponse getInitialTip() {

        try {
            // Direct mapping to TipResponse without the ApiResponse wrapper
            ResponseEntity<TipResponse> response = restTemplate.exchange(
                    BASE_URL + "/tip",
                    HttpMethod.GET,
                    null,
                    TipResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to get initial tip: Unknown error");
            }
        } catch (Exception e) {
            System.err.println("Error getting initial tip: " + e.getMessage());
            throw new RuntimeException("Challenge 1 failed", e);
        }
    }

    @Override
    public RegistrationResponse register(RegisterRequest request) {
        System.out.println("Challenge 2: Registering as a detective...");

        try {
            // Create HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HTTP entity with the request body and headers
            HttpEntity<RegisterRequest> entity = new HttpEntity<>(request, headers);

            // Make a POST request to the registration endpoint
            ResponseEntity<ApiResponse<RegistrationResponse>> response = restTemplate.exchange(
                    BASE_URL + "/detectives/register",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<ApiResponse<RegistrationResponse>>() {}
            );

            if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {

                return response.getBody().getData();
            } else {
                throw new RuntimeException("Failed to register: " +
                        (response.getBody() != null ? response.getBody().getMessage() : "Unknown error"));
            }
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            throw new RuntimeException("Challenge 2 failed", e);
        }
    }

    @Override
    public EvidenceResponse getWitnessEvidence(String token) {
        System.out.println("Challenge 3: Getting witness evidence with JWT authentication...");

        try {
            // Create HTTP headers with JWT authentication
            HttpHeaders headers = createAuthHeaders(token);

            // Create HTTP entity with headers
            HttpEntity<?> entity = new HttpEntity<>(headers);

            // Make a GET request to the witness endpoint
            ResponseEntity<ApiResponse<EvidenceResponse>> response = restTemplate.exchange(
                    BASE_URL + "/evidence/witness1",
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<ApiResponse<EvidenceResponse>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                EvidenceResponse evidenceResponse = response.getBody().getData();

                System.out.println("Witness evidence received successfully!");
                System.out.println("Encoded Evidence: " + evidenceResponse.getEncodedEvidence());
                System.out.println("Evidence Type: " + evidenceResponse.getEvidenceType());
                System.out.println("Next Step Hint: " + evidenceResponse.getNextStepHint());

                // Decode the Base64 encoded evidence
                String decodedEvidence = decodeBase64(evidenceResponse.getEncodedEvidence());
                System.out.println("Decoded Evidence: " + decodedEvidence);

                return evidenceResponse;
            } else {
                throw new RuntimeException("Failed to get witness evidence: " +
                        (response.getBody() != null ? response.getBody().getMessage() : "Unknown error"));
            }
        } catch (Exception e) {
            System.err.println("Error getting witness evidence: " + e.getMessage());
            throw new RuntimeException("Challenge 3 failed", e);
        }
    }

    @Override
    public ForensicsResponse analyzeForensics(ForensicsRequest request, String token) {
        System.out.println("Challenge 4: Analyzing forensic evidence with request signing...");

        try {
            // Create HTTP headers with JWT authentication
            HttpHeaders headers = createAuthHeaders(token);

            // Convert request to JSON string for signing
            String requestJson = new ObjectMapper().writeValueAsString(request);

            // Create HMAC-SHA256 signature
            String signature = createHmacSha256Signature(requestJson, "detective_secret_key");

            // Add the signature header
            headers.set("X-Detective-Signature", signature);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HTTP entity with the request body and headers
            HttpEntity<ForensicsRequest> entity = new HttpEntity<>(request, headers);

            // Make a POST request to the forensics endpoint
            ResponseEntity<ApiResponse<ForensicsResponse>> response = restTemplate.exchange(
                    BASE_URL + "/forensics/analyze",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<ApiResponse<ForensicsResponse>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ForensicsResponse forensicsResponse = response.getBody().getData();

                System.out.println("Forensic analysis completed successfully!");
                System.out.println("Analysis Result: " + forensicsResponse.getAnalysisResult());
                System.out.println("Forensic Report: " + forensicsResponse.getForensicReport());
                System.out.println("Next Step Hint: " + forensicsResponse.getNextStepHint());

                return forensicsResponse;
            } else {
                throw new RuntimeException("Failed to analyze forensics: " +
                        (response.getBody() != null ? response.getBody().getMessage() : "Unknown error"));
            }
        } catch (Exception e) {
            System.err.println("Error analyzing forensics: " + e.getMessage());
            throw new RuntimeException("Challenge 4 failed", e);
        }
    }

    @Override
    public SuspectResponse interrogateSuspect(String token) {
        System.out.println("Challenge 5: Interrogating suspect with content negotiation...");

        // Try different content types to find the one that works
        List<MediaType> contentTypes = Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML,
                MediaType.TEXT_PLAIN
        );

        for (MediaType contentType : contentTypes) {
            try {
                // Create HTTP headers with JWT authentication and specific Accept header
                HttpHeaders headers = createAuthHeaders(token);
                headers.setAccept(Collections.singletonList(contentType));

                // Create HTTP entity with headers
                HttpEntity<?> entity = new HttpEntity<>(headers);

                // Make a GET request to the suspect interview endpoint
                ResponseEntity<ApiResponse<SuspectResponse>> response = restTemplate.exchange(
                        BASE_URL + "/suspects/interview",
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<SuspectResponse>>() {}
                );

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    SuspectResponse suspectResponse = response.getBody().getData();

                    System.out.println("Suspect interrogation successful with content type: " + contentType);
                    System.out.println("Suspect Statement: " + suspectResponse.getSuspectStatement());
                    System.out.println("Alibi: " + suspectResponse.getAlibi());
                    System.out.println("Evidence Connection: " + suspectResponse.getEvidenceConnection());
                    System.out.println("Next Step Hint: " + suspectResponse.getNextStepHint());

                    // If we found the valuable information (XML format), return it
                    if (suspectResponse.getEvidenceConnection() != null &&
                            !suspectResponse.getEvidenceConnection().isEmpty()) {
                        return suspectResponse;
                    }

                    // Otherwise, try the next content type
                    System.out.println("This content type didn't provide complete information. Trying another...");
                }
            } catch (HttpClientErrorException e) {
                System.out.println("Content type " + contentType + " failed: " + e.getMessage());
                // Continue to the next content type
            } catch (Exception e) {
                System.err.println("Error during suspect interrogation with content type " +
                        contentType + ": " + e.getMessage());
                // Continue to the next content type
            }
        }

        throw new RuntimeException("Challenge 5 failed: Could not get valuable information from the suspect");
    }

    @Override
    public List<SurveillanceResponse> conductSurveillance(String token) {
        System.out.println("Challenge 6: Conducting surveillance with rate limiting and backoff...");

        List<SurveillanceResponse> observations = new ArrayList<>();
        int retryCount = 0;
        int successfulObservations = 0;
        int requiredObservations = 3; // We need 3 successful observations

        while (successfulObservations < requiredObservations && retryCount < 10) {
            try {
                // Create HTTP headers with JWT authentication
                HttpHeaders headers = createAuthHeaders(token);

                // Create HTTP entity with headers
                HttpEntity<?> entity = new HttpEntity<>(headers);

                // Make a GET request to the surveillance endpoint
                ResponseEntity<ApiResponse<SurveillanceResponse>> response = restTemplate.exchange(
                        BASE_URL + "/surveillance",
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<SurveillanceResponse>>() {}
                );

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    SurveillanceResponse surveillanceResponse = response.getBody().getData();
                    observations.add(surveillanceResponse);
                    successfulObservations++;

                    System.out.println("Surveillance observation " + successfulObservations +
                            " recorded successfully!");
                    System.out.println("Observation Data: " + surveillanceResponse.getObservationData());
                    System.out.println("Location Details: " + surveillanceResponse.getLocationDetails());
                    System.out.println("Suspicious Activity: " + surveillanceResponse.getSuspiciousActivity());
                    System.out.println("Next Step Hint: " + surveillanceResponse.getNextStepHint());
                    System.out.println("Remaining Attempts: " + surveillanceResponse.getRemainingAttempts());

                    // If we've completed all required observations, we're done
                    if (successfulObservations >= requiredObservations ||
                            (surveillanceResponse.getRemainingAttempts() != null &&
                                    surveillanceResponse.getRemainingAttempts() == 0)) {
                        break;
                    }

                    // Wait a bit before the next observation to avoid rate limiting
                    long delayMillis = calculateBackoffDelay(retryCount);
                    System.out.println("Waiting " + (delayMillis / 1000) +
                            " seconds before next observation...");
                    Thread.sleep(delayMillis);
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    retryCount++;

                    // Get retry delay from header if available
                    HttpHeaders headers = e.getResponseHeaders();
                    long retryAfterSeconds = 0;
                    if (headers != null && headers.containsKey("X-Rate-Limit-Retry-After-Seconds")) {
                        retryAfterSeconds = Long.parseLong(
                                Objects.requireNonNull(
                                        headers.getFirst("X-Rate-Limit-Retry-After-Seconds")
                                )
                        );
                    } else {
                        // Use exponential backoff if header not available
                        retryAfterSeconds = calculateBackoffDelay(retryCount) / 1000;
                    }

                    System.out.println("Rate limit exceeded. Waiting " + retryAfterSeconds +
                            " seconds before retry...");
                    try {
                        Thread.sleep(retryAfterSeconds * 1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.err.println("Error during surveillance: " + e.getMessage());
                    throw new RuntimeException("Challenge 6 failed", e);
                }
            } catch (Exception e) {
                System.err.println("Error during surveillance: " + e.getMessage());
                throw new RuntimeException("Challenge 6 failed", e);
            }
        }

        if (successfulObservations < requiredObservations) {
            throw new RuntimeException("Challenge 6 failed: Could not complete required observations");
        }

        System.out.println("Surveillance completed successfully with " + observations.size() +
                " observations!");
        return observations;
    }

//    @Override
//    public SecureFileResponse accessSecureFiles(String token) {
//        System.out.println("Challenge 7: Accessing secure files with OAuth2 flow...");
//
//        try {
//            // Step 1: Get authorization code
//            String clientId = "detective_client";
//            String redirectUri = "http://localhost:8081/callback";
//            String state = UUID.randomUUID().toString();
//
//            HttpHeaders authHeaders = createAuthHeaders(token);
//            UriComponentsBuilder authUriBuilder = UriComponentsBuilder.fromHttpUrl(
//                            BASE_URL + "/headquarters/authorize")
//                    .queryParam("client_id", clientId)
//                    .queryParam("redirect_uri", redirectUri)
//                    .queryParam("response_type", "code")
//                    .queryParam("state", state);
//
//            HttpEntity<?> authEntity = new HttpEntity<>(authHeaders);
//
//            ResponseEntity<ApiResponse<Map<String, String>>> authResponse = restTemplate.exchange(
//                    authUriBuilder.toUriString(),
//                    HttpMethod.GET,
//                    authEntity,
//                    new ParameterizedTypeReference<ApiResponse<Map<String, String>>>() {}
//            );
//
//            if (authResponse.getStatusCode() != HttpStatus.OK || authResponse.getBody() == null ||
//                    authResponse.getBody().getData() == null) {
//                throw new RuntimeException("Failed to get authorization code");
//            }
//
//            String authCode = authResponse.getBody().getData().get("code");
//            String responseState = authResponse.getBody().getData().get("state");
//
//            if (!state.equals(responseState)) {
//                throw new RuntimeException("State mismatch, possible CSRF attack");
//            }
//
//            System.out.println("Authorization code received successfully!");
//
//            // Step 2: Exchange authorization code for access token
//            HttpHeaders tokenHeaders = new HttpHeaders();
//            tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//            MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
//            tokenParams.add("grant_type", "authorization_code");
//            tokenParams.add("code", authCode);
//            tokenParams.add("client_id", clientId);
//            tokenParams.add("client_secret", "detective_secret");
//
//            HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenParams, tokenHeaders);
//
//            ResponseEntity<ApiResponse<Map<String, String>>> tokenResponse = restTemplate.exchange(
//                    BASE_URL + "/headquarters/token",
//                    HttpMethod.POST,
//                    tokenEntity,
//                    new ParameterizedTypeReference<ApiResponse<Map<String, String>>>() {}
//            );
//
//            if (tokenResponse.getStatusCode() != HttpStatus.OK || tokenResponse.getBody() == null ||
//                    tokenResponse.getBody().getData() == null) {
//                throw new RuntimeException("Failed to get access token");
//            }
//
//            String accessToken = tokenResponse.getBody().getData().get("access_token");
//            System.out.println("Access token received successfully!");
//
//            // Step 3: Access secure files with the access token
//            HttpHeaders fileHeaders = new HttpHeaders();
//            fileHeaders.setBearerAuth(accessToken);
//
//            HttpEntity<?> fileEntity = new HttpEntity<>(fileHeaders);
//
//            ResponseEntity<ApiResponse<SecureFileResponse>> fileResponse = restTemplate.exchange(
//                    BASE_URL + "/headquarters/securefiles",
//                    HttpMethod.GET,
//                    fileEntity,
//                    new ParameterizedTypeReference<ApiResponse<SecureFileResponse>>() {}
//            );
//
//            if (fileResponse.getStatusCode() == HttpStatus.OK && fileResponse.getBody() != null) {
//                SecureFileResponse secureFileResponse = fileResponse.getBody().getData();
//
//                System.out.println("Secure files accessed successfully!");
//                System.out.println("File Content: " + secureFileResponse.getFileContent());
//                System.out.println("Case Resolution: " + secureFileResponse.getCaseResolution());
//                System.out.println("Congratulations Message: " + secureFileResponse.getCongratulationsMessage());
//
//                return secureFileResponse;
//            } else {
//                throw new RuntimeException("Failed to access secure files: " +
//                        (fileResponse.getBody() != null ? fileResponse.getBody().getMessage() : "Unknown error"));
//            }
//        } catch (Exception e) {
//            System.err.println("Error accessing secure files: " + e.getMessage());
//            throw new RuntimeException("Challenge 7 failed", e);
//        }
//    }

    // --- Helper methods ---

    /**
     * Creates HTTP headers with JWT token for authentication.
     *
     * @param token JWT token
     * @return HttpHeaders with Authorization header
     */
    private HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }

    /**
     * Calculates exponential backoff delay.
     *
     * @param retryCount current retry count
     * @return delay time in milliseconds
     */
    private long calculateBackoffDelay(int retryCount) {
        // Exponential backoff with jitter
        long baseDelay = 1000; // 1 second base
        long maxDelay = 60000; // 60 seconds max

        // Calculate exponential delay: 2^retryCount * baseDelay
        long delay = (long) (Math.pow(2, retryCount) * baseDelay);

        // Add some randomness (jitter) to avoid synchronized retries
        delay += (long) (delay * 0.1 * Math.random());

        // Cap at max delay
        return Math.min(delay, maxDelay);
    }

    /**
     * Creates an HMAC-SHA256 signature for the given message.
     *
     * @param message the message to sign
     * @param key the secret key
     * @return hex string of the HMAC-SHA256 signature
     * @throws NoSuchAlgorithmException if the algorithm is not available
     * @throws InvalidKeyException if the key is invalid
     */
    private String createHmacSha256Signature(String message, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(
                key.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );
        sha256Hmac.init(secretKey);

        byte[] hash = sha256Hmac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    /**
     * Converts byte array to hexadecimal string.
     *
     * @param bytes the byte array
     * @return hex string representation
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Decodes a Base64 encoded string.
     *
     * @param encoded Base64 encoded string
     * @return decoded string
     */
    private String decodeBase64(String encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Extracts the evidence code from a JWT token.
     * This is a simplified implementation for demonstration purposes.
     *
     * @param token JWT token
     * @return evidence code
     */
    private String extractEvidenceCodeFromJwt(String token) {
        try {
            // Split the token into parts
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return "Invalid token format";
            }

            // Decode the payload (second part)
            String payload = new String(
                    Base64.getUrlDecoder().decode(parts[1]),
                    StandardCharsets.UTF_8
            );

            // Parse JSON and extract evidence code
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> payloadMap = mapper.readValue(payload, Map.class);

            return payloadMap.containsKey("evidenceCode")
                    ? payloadMap.get("evidenceCode").toString()
                    : "Evidence code not found";

        } catch (Exception e) {
            System.err.println("Error extracting evidence code from JWT: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}