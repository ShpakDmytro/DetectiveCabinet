package org.shad.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.shad.model.dto.ApiResponse;
import org.shad.model.dto.request.AuthRequest;
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
import org.shad.service.ApiClientService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of the ApiClientService interface.
 * This class provides methods to interact with the Detective Challenge API.
 */
public class ApiClientServiceImpl implements ApiClientService {

    private static final String BASE_URL = "http://104.194.148.55:8080/api/v1";
    private final RestTemplate restTemplate;
    private String jwtToken;

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
        //TODO - look BASE_URL and need to add /tip in URL
        throw new UnsupportedOperationException();
    }

    /**
     * Registers a new detective using the provided registration request.
     *
     * <p>This method sends a POST request to the endpoint {@code BASE_URL + "/detectives/register"}
     * with the given {@link RegisterRequest}. The response is expected to be wrapped in an
     * {@link ApiResponse} containing a {@link RegistrationResponse}. If the registration
     * is successful (HTTP 201 Created), the method extracts and returns the response data.</p>
     *
     * <p>If an error occurs during registration, a {@link RuntimeException} is thrown.</p>
     *
     * @param request the {@link RegisterRequest} containing registration details.
     * @return {@link RegistrationResponse} containing the registration result.
     * @throws RuntimeException if the registration request fails or returns an invalid response.
     */
    @Override
    public RegistrationResponse register(RegisterRequest request) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Authenticates a detective using the provided login credentials.
     *
     * <p>This method sends a POST request to the endpoint {@code BASE_URL + "/detectives/login"}
     * with the given {@link AuthRequest}. The response is expected to be wrapped in an
     * {@link ApiResponse} containing an {@link AuthResponse}. If authentication is successful
     * (HTTP 200 OK), the method extracts and returns the authentication details.</p>
     *
     * <p>If an error occurs during login, a {@link RuntimeException} is thrown.</p>
     *
     * @param request the {@link AuthRequest} containing login credentials.
     * @return {@link AuthResponse} containing authentication details such as token and user information.
     * @throws RuntimeException if the login request fails or returns an invalid response.
     */
    @Override
    public AuthResponse login(AuthRequest request) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves witness evidence using JWT authentication.
     *
     * <p>This method sends a GET request to the endpoint {@code BASE_URL + "/evidence/witness1"}
     * with the provided JWT token for authentication. The response is expected to be wrapped in an
     * {@link ApiResponse} containing an {@link EvidenceResponse}. If the request is successful
     * (HTTP 200 OK), the method extracts and returns the evidence details.</p>
     *
     * <p>The method also decodes the Base64-encoded evidence and prints additional
     * information, such as the evidence type and next step hint.</p>
     *
     * <p>If an error occurs during the request, a {@link RuntimeException} is thrown.</p>
     *
     * @param token the JWT authentication token required for accessing the witness evidence.
     * @return {@link EvidenceResponse} containing witness evidence details.
     * @throws RuntimeException if the request fails or returns an invalid response.
     */
    @Override
    public EvidenceResponse getWitnessEvidence(String token) {
        //TODO and need implement private method decodeBase64 bellow
        throw new UnsupportedOperationException();
    }

    /**
     * Analyzes forensic evidence using request signing for authentication.
     *
     * <p>This method sends a POST request to the endpoint {@code BASE_URL + }
     * with the given {@link ForensicsRequest}. The request body is first converted to a JSON string,
     * signed using an HMAC-SHA256 signature with a secret key, and included in the request headers.
     * The JWT token is also used for authentication.</p>
     *
     * <p>The response is expected to be wrapped in an {@link ApiResponse} containing a
     * {@link ForensicsResponse}. If the request is successful (HTTP 200 OK), the method extracts
     * and returns the forensic analysis details.</p>
     *
     * <p>If an error occurs during the request, a {@link RuntimeException} is thrown.</p>
     *
     * @param request the {@link ForensicsRequest} containing the forensic evidence to be analyzed.
     * @param token   the JWT authentication token required for accessing the forensic analysis API.
     * @return {@link ForensicsResponse} containing the results of the forensic analysis.
     * @throws RuntimeException if the request fails or returns an invalid response.
     */
    @Override
    public ForensicsResponse analyzeForensics(ForensicsRequest request, String token) {
        //TODO and also need implement createHmacSha256Signature method bellow
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to interrogate a suspect using content negotiation.
     *
     * <p>This method sends a GET request to the endpoint {@code BASE_URL + "/suspects/interview"}
     * with the provided JWT authentication token. It tries multiple content types (JSON, XML, and Plain Text)
     * to determine the format that provides the most valuable information.</p>
     *
     * <p>If the response contains a {@link SuspectResponse} with useful evidence connections,
     * the method returns it. Otherwise, it iterates through different content types
     * until a valid response is obtained.</p>
     *
     * <p>If all attempts fail, a {@link RuntimeException} is thrown.</p>
     *
     * @param token the JWT authentication token required for accessing the suspect interrogation API.
     * @return {@link SuspectResponse} containing details obtained from the interrogation.
     * @throws RuntimeException if no valuable information is retrieved after trying all content types.
     */
    @Override
    public SuspectResponse interrogateSuspect(String token) {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Conducts surveillance with rate limiting and backoff handling.
     *
     * <p>This method sends a GET request to the endpoint {@code BASE_URL + "/surveillance"}
     * with the provided JWT authentication token. It attempts to collect at least three successful
     * observations while handling potential rate limiting by applying exponential backoff.</p>
     *
     * <p>If a rate limit is exceeded (HTTP 429 Too Many Requests), the method respects the retry delay
     * provided in the response headers or calculates an exponential backoff delay.</p>
     *
     * <p>The method stops when the required number of successful observations is reached
     * or when all allowed retries are exhausted.</p>
     *
     * <p>If all attempts fail, a {@link RuntimeException} is thrown.</p>
     *
     * @param token the JWT authentication token required for accessing the surveillance API.
     * @return a list of {@link SurveillanceResponse} objects containing the observations.
     * @throws RuntimeException if the required number of successful observations cannot be completed.
     */
    @Override
    public List<SurveillanceResponse> conductSurveillance(String token) {
        //TODO and also need implement calculateBackoffDelay
        throw new UnsupportedOperationException();
    }

    @Override
    public SecureFileResponse accessSecureFiles(String token) {
        //TODO
        throw new UnsupportedOperationException();
    }

    // --- Helper methods ---


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

        // Add some randomness (jitter) to avoid synchronized retries

        // Cap at max delay
        throw new UnsupportedOperationException();
    }

    /**
     * Creates an HMAC-SHA256 signature for the given message.
     *
     * @param message the message to sign
     * @param key     the secret key
     * @return hex string of the HMAC-SHA256 signature
     * @throws NoSuchAlgorithmException if the algorithm is not available
     * @throws InvalidKeyException      if the key is invalid
     */
    private String createHmacSha256Signature(String message, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        //TODO - use prepared method bytesToHex bellow in body this method
        throw new UnsupportedOperationException();
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
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Extracts the evidence code from a JWT token.
     * This is a simplified implementation for demonstration purposes.
     *
     * @param token JWT token
     * @return evidence code
     */
    private String extractEvidenceCodeFromJwt(String token) {
        //TODO
        throw new UnsupportedOperationException();
    }
}
