package org.shad.service;

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

import java.util.List;

/**
 * Service interface for the API Detective Challenge client.
 * Defines methods to interact with each challenge endpoint.
 */
public interface ApiClientService {

    /**
     * Challenge 1: Gets the initial tip to start the investigation.
     *
     * @return the tip response with information and hints
     */
    TipResponse getInitialTip();

    /**
     * Challenge 2: Registers as a detective.
     *
     * @param request registration details including username, email, and password
     * @return registration response with hints
     */
    RegistrationResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest request);
    /**
     * Challenge 4: Gets the witness statement using JWT authentication.
     * The response is encoded and needs to be decoded.
     *
     * @param token JWT token from registration/login
     * @return encoded evidence response
     */
    EvidenceResponse getWitnessEvidence(String token);

    /**
     * Challenge 5: Analyzes forensic evidence with request signing.
     * Requires signing the request with HMAC-SHA256.
     *
     * @param request forensics analysis request
     * @param token JWT token from registration/login
     * @return forensics analysis response
     */
    ForensicsResponse analyzeForensics(ForensicsRequest request, String token);

    /**
     * Challenge 6: Interrogates a suspect with content negotiation.
     * Requires using the correct Accept header to get valuable information.
     *
     * @param token JWT token from registration/login
     * @return suspect response with information about the next step
     */
    SuspectResponse interrogateSuspect(String token);

    /**
     * Challenge 7: Conducts surveillance with rate limiting and backoff.
     * Requires implementing proper exponential backoff to handle rate limits.
     *
     * @param token JWT token from registration/login
     * @return list of surveillance responses from multiple observations
     */
    List<SurveillanceResponse> conductSurveillance(String token);

    /**
     * Challenge 8: Accesses secure files with OAuth2 flow.
     * Requires implementing the OAuth2 authorization code flow.
     *
     * @param token JWT token from registration/login
     * @return secure file response with the case resolution
     */
    SecureFileResponse accessSecureFiles(String token);
}