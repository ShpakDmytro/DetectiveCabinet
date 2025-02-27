package org.shad;

import org.shad.model.dto.request.ForensicsRequest;
import org.shad.model.dto.request.RegisterRequest;
import org.shad.model.dto.response.EvidenceResponse;
import org.shad.model.dto.response.ForensicsResponse;
import org.shad.model.dto.response.RegistrationResponse;
import org.shad.model.dto.response.SurveillanceResponse;
import org.shad.model.dto.response.SuspectResponse;
import org.shad.model.dto.response.TipResponse;
import org.shad.service.ApiClientService;
import org.shad.service.impl.ApiClientServiceImpl;

import java.util.List;
import java.util.Scanner;

public class DetectiveCabinet {

    private static final ApiClientService apiClient = new ApiClientServiceImpl();
    private static String jwtToken = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void tryCks (String[] args) {
        System.out.println("===== API Detective Challenge =====");
        System.out.println("Welcome, detective! You've been assigned to investigate a major digital heist.\n");

        try {
            // Challenge 1: Get the initial tip
            System.out.println("\n===== CHALLENGE 1: THE INITIAL TIP =====");
            System.out.print("Press Enter to get the initial anonymous tip...");
            scanner.nextLine();

            TipResponse tipResponse = apiClient.getInitialTip();

            // Challenge 2: Register as a detective
            System.out.println("\n===== CHALLENGE 2: DETECTIVE REGISTRATION =====");
            RegisterRequest registerRequest = createRegistrationRequest();
            RegistrationResponse authResponse = apiClient.register(registerRequest);


            // Challenge 3: Get witness evidence
            System.out.println("\n===== CHALLENGE 3: FIRST WITNESS =====");
            System.out.print("Press Enter to interview the first witness...");
            scanner.nextLine();

            EvidenceResponse evidenceResponse = apiClient.getWitnessEvidence(jwtToken);

            // Challenge 4: Analyze forensic evidence
            System.out.println("\n===== CHALLENGE 4: DIGITAL FORENSICS =====");
            ForensicsRequest forensicsRequest = createForensicsRequest();

            ForensicsResponse forensicsResponse = apiClient.analyzeForensics(forensicsRequest, jwtToken);

            // Challenge 5: Interrogate suspect
            System.out.println("\n===== CHALLENGE 5: SUSPECT INTERROGATION =====");
            System.out.print("Press Enter to interrogate the suspect...");
            scanner.nextLine();

            SuspectResponse suspectResponse = apiClient.interrogateSuspect(jwtToken);

            // Challenge 6: Conduct surveillance
            System.out.println("\n===== CHALLENGE 6: SURVEILLANCE OPERATION =====");
            System.out.print("Press Enter to begin surveillance operation...");
            scanner.nextLine();

            List<SurveillanceResponse> surveillanceResponses = apiClient.conductSurveillance(jwtToken);

//            // Challenge 7: Access secure files
//            System.out.println("\n===== CHALLENGE 7: HEADQUARTERS ACCESS =====");
//            System.out.print("Press Enter to access secure headquarters files...");
//            scanner.nextLine();
//
//            SecureFileResponse secureFileResponse = apiClient.accessSecureFiles(jwtToken);
//
//            // Display case resolution
//            System.out.println("\n===== CASE RESOLVED =====");
//            System.out.println(secureFileResponse.getCongratulationsMessage());

        } catch (Exception e) {
            System.err.println("\n⚠️ Investigation failed: " + e.getMessage());
            e.printStackTrace();
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

    /**
     * Creates a forensics request with user input.
     *
     * @return ForensicsRequest with analysis details
     */
    private static ForensicsRequest createForensicsRequest() {
        System.out.println("Please provide sample data for forensic analysis:");

        System.out.print("Sample data: ");
        String sampleData = scanner.nextLine();

        System.out.print("Analysis type: ");
        String analysisType = scanner.nextLine();

        return ForensicsRequest.builder()
                .sampleData(sampleData)
                .analysisType(analysisType)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
