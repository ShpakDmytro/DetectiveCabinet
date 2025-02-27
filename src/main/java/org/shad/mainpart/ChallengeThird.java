package org.shad.warmup;

import org.shad.model.dto.request.AuthRequest;
import org.shad.model.dto.request.RegisterRequest;
import org.shad.model.dto.response.AuthResponse;
import org.shad.model.dto.response.RegistrationResponse;
import org.shad.service.ApiClientService;
import org.shad.service.impl.ApiClientServiceImpl;

import java.util.Scanner;

public class ChallengeThird {
    private static final ApiClientService apiClient = new ApiClientServiceImpl();
    private static String jwtToken = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {

        System.out.println("\n===== CHALLENGE 3: DETECTIVE LOGIN =====");

        try {
            // Challenge 3: Login as a detective
            AuthRequest authRequest = createAuthRequest();

            AuthResponse authResponse = apiClient.login(authRequest);

            System.out.println("Login successful!");
            System.out.println("Next Challenge Hint: " + authResponse.getNextChallengeHint());
            System.out.println("JWT Token received");



        } catch (Exception e) {
            System.err.println("\n⚠️ Investigation failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    /**
     * Creates an auth request with user input.
     *
     * @return RegisterRequest with user details
     */
    private static AuthRequest createAuthRequest() {
        System.out.println("Please provide your detective credentials to login:");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password (must include uppercase, lowercase, digit, and special character): ");
        String password = scanner.nextLine();

        return AuthRequest.builder()
                .username(username)
                .password(password)
                .build();
    }
}
