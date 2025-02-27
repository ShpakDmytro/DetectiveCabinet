package org.shad.mainpart;

import org.shad.model.dto.request.ForensicsRequest;
import org.shad.model.dto.request.RegisterRequest;
import org.shad.model.dto.response.EvidenceResponse;
import org.shad.model.dto.response.ForensicsResponse;
import org.shad.model.dto.response.RegistrationResponse;
import org.shad.model.dto.response.SecureFileResponse;
import org.shad.model.dto.response.SurveillanceResponse;
import org.shad.model.dto.response.SuspectResponse;
import org.shad.model.dto.response.TipResponse;
import org.shad.service.ApiClientService;
import org.shad.service.impl.ApiClientServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ChallengeEighth {

    private static final ApiClientService apiClient = new ApiClientServiceImpl();
    private static String jwtToken = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {

        try {

            // Challenge 8: Access secure files
            System.out.println("\n===== CHALLENGE 8: HEADQUARTERS ACCESS =====");
            System.out.print("Press Enter to access secure headquarters files...");
            scanner.nextLine();

            SecureFileResponse secureFileResponse = apiClient.accessSecureFiles(jwtToken);

            // Display case resolution
            System.out.println("\n===== CASE RESOLVED =====");
            System.out.println(secureFileResponse.getCongratulationsMessage());

        } catch (Exception e) {
            System.err.println("\n⚠️ Investigation failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

}