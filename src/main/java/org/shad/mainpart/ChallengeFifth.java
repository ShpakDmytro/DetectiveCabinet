package org.shad.mainpart;

import org.shad.model.dto.request.ForensicsRequest;
import org.shad.model.dto.response.ForensicsResponse;
import org.shad.service.ApiClientService;
import org.shad.service.impl.ApiClientServiceImpl;

import java.util.Scanner;

public class ChallengeFifth {

    private static final ApiClientService apiClient = new ApiClientServiceImpl();
    private static String jwtToken = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {

        try {
            System.out.println("\n===== CHALLENGE 5: DIGITAL FORENSICS =====");

            ForensicsRequest forensicsRequest = createForensicsRequest();
            ForensicsResponse forensicsResponse = apiClient.analyzeForensics(forensicsRequest, jwtToken);

            System.out.println("Forensic analysis completed successfully!");
            System.out.println("Analysis Result: " + forensicsResponse.getAnalysisResult());
            System.out.println("Forensic Report: " + forensicsResponse.getForensicReport());
            System.out.println("Next Step Hint: " + forensicsResponse.getNextStepHint());

        } catch (Exception e) {
            System.err.println("\n⚠️ Investigation failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
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
