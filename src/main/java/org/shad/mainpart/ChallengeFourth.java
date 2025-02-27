package org.shad.mainpart;

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

public class ChallengeFourth {
    private static final ApiClientService apiClient = new ApiClientServiceImpl();
    private static String jwtToken = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {

            // Challenge 3: Get witness evidence
            System.out.println("\n===== CHALLENGE 4: FIRST WITNESS =====");
            System.out.print("Press Enter to interview the first witness...");
            scanner.nextLine();

            EvidenceResponse evidenceResponse = apiClient.getWitnessEvidence(jwtToken);

            System.out.println("Witness evidence received successfully!");
            System.out.println("Encoded Evidence: " + evidenceResponse.getEncodedEvidence());
            System.out.println("Evidence Type: " + evidenceResponse.getEvidenceType());
            System.out.println("Next Step Hint: " + evidenceResponse.getNextStepHint());


        } catch (Exception e) {
            System.err.println("\n⚠️ Investigation failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
