package org.shad.mainpart;

import org.shad.model.dto.response.SurveillanceResponse;
import org.shad.service.ApiClientService;
import org.shad.service.impl.ApiClientServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ChallengeSeventh {
    private static final ApiClientService apiClient = new ApiClientServiceImpl();
    private static String jwtToken = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {

        try {

            // Challenge 7: Conduct surveillance
            System.out.println("\n===== CHALLENGE 7: SURVEILLANCE OPERATION =====");
            System.out.print("Press Enter to begin surveillance operation...");
            scanner.nextLine();

            List<SurveillanceResponse> surveillanceResponses = apiClient.conductSurveillance(jwtToken);

        } catch (Exception e) {
            System.err.println("\n⚠️ Investigation failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}