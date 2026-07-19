package in.vaibhav.moneymanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.from.email}")
    private String fromEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BREVO_URL =
            "https://api.brevo.com/v3/smtp/email";

    // ==========================
    // Normal Email
    // ==========================

    public void sendEmail(String to,
                          String subject,
                          String body) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", "application/json");
        headers.set("api-key", apiKey);

        Map<String, Object> requestBody = Map.of(

                "sender", Map.of(
                        "name", "Expense Manager",
                        "email", fromEmail
                ),

                "to", List.of(
                        Map.of(
                                "email", to
                        )
                ),

                "subject", subject,

                "htmlContent", body
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        BREVO_URL,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        if (!response.getStatusCode().is2xxSuccessful()) {

            throw new RuntimeException(
                    "Failed to send email : "
                            + response.getBody());

        }
    }

    // ==========================
    // Email With Attachment
    // ==========================

    public void sendEmailWithAttachment(
            String to,
            String subject,
            String body,
            byte[] fileBytes,
            String fileName) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", "application/json");
        headers.set("api-key", apiKey);

        String encodedFile =
                Base64.getEncoder()
                        .encodeToString(fileBytes);

        Map<String, Object> requestBody = Map.of(

                "sender", Map.of(
                        "name", "Expense Manager",
                        "email", fromEmail
                ),

                "to", List.of(
                        Map.of(
                                "email", to
                        )
                ),

                "subject", subject,

                "htmlContent", body,

                "attachment", List.of(

                        Map.of(
                                "name", fileName,
                                "content", encodedFile
                        )

                )

        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        BREVO_URL,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        if (!response.getStatusCode().is2xxSuccessful()) {

            throw new RuntimeException(
                    "Failed to send attachment : "
                            + response.getBody());

        }
    }
}