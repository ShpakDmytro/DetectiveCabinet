package org.shad.warmup;

import org.shad.model.dto.request.RegisterRequest;
import org.shad.model.dto.response.RegistrationResponse;
import org.shad.service.ApiClientService;
import org.shad.service.impl.ApiClientServiceImpl;

import java.util.Scanner;

public class ChallengeSecond {

    private static final ApiClientService apiClient = new ApiClientServiceImpl();
    private static String jwtToken = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {

        System.out.println("\n===== CHALLENGE 2: DETECTIVE REGISTRATION =====");

        try {
            // Challenge 2: Register as a detective
            RegisterRequest registerRequest = createRegistrationRequest();

            RegistrationResponse registrationResponse = apiClient.register(registerRequest);


            System.out.println("Registration successful!");
            System.out.println("Username: " + registrationResponse.getUsername());
            System.out.println("Next Challenge Hint: " + registrationResponse.getNextChallengeHint());

        } catch (Exception e) {
            System.err.println("\n⚠️ Investigation failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    /**
     * Creates a registration request with user input.
     *
     * @return RegisterRequest with user details
     */
    private static RegisterRequest createRegistrationRequest() {
        System.out.println("Please provide your detective credentials to register:");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password (must include uppercase, lowercase, digit, and special character): ");
        String password = scanner.nextLine();

        return RegisterRequest.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
