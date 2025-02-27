package org.shad.warmup;

import org.shad.model.dto.response.TipResponse;
import org.shad.service.ApiClientService;
import org.shad.service.impl.ApiClientServiceImpl;

import java.util.Scanner;

public class ChallengeFirst {

    private static final ApiClientService apiClient = new ApiClientServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== API Detective Challenge =====");
        System.out.println("Welcome, detective! You've been assigned to investigate a major digital heist.\n");
        System.out.println("\n===== CHALLENGE 1: THE INITIAL TIP =====");

        try {
            // Challenge 1: Get the initial tip
            System.out.print("Press Enter to get the initial anonymous tip...");
            scanner.nextLine();

            TipResponse tipResponse = apiClient.getInitialTip();

            System.out.println("Initial tip received successfully!");
            System.out.println("Tip Message: " + tipResponse.getTipMessage());
            System.out.println("Next Step Hint: " + tipResponse.getNextStepHint());

        } catch (Exception e) {
            System.err.println("\n⚠️ Investigation failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
