package org.shad.mainpart;

import org.shad.model.dto.response.SuspectResponse;
import org.shad.service.ApiClientService;
import org.shad.service.impl.ApiClientServiceImpl;

import java.util.Scanner;

public class ChallengeSixth {

    private static final ApiClientService apiClient = new ApiClientServiceImpl();
    private static String jwtToken = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {

        try {
            // Challenge 6: Interrogate suspect
            System.out.println("\n===== CHALLENGE 6: SUSPECT INTERROGATION =====");
            System.out.print("Press Enter to interrogate the suspect...");
            scanner.nextLine();

            SuspectResponse suspectResponse = apiClient.interrogateSuspect(jwtToken);

            System.out.println("Suspect Statement: " + suspectResponse.getSuspectStatement());
            System.out.println("Alibi: " + suspectResponse.getAlibi());
            System.out.println("Next Step Hint: " + suspectResponse.getNextStepHint());

        } catch (Exception e) {
            System.err.println("\n⚠️ Investigation failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}