package dzap.application.service;

import dzap.application.dto.brevo.BrevoContactRequest;
import dzap.application.dto.brevo.BrevoContactResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Service pour l'intégration avec Brevo (Sendinblue)
 * API Doc: https://developers.brevo.com/docs/synchronise-contact-lists
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BrevoService {

    private final RestTemplate restTemplate;

    @Value("${brevo.api-key:}")
    private String brevoApiKey;

    @Value("${brevo.api-url:https://api.brevo.com/v3}")
    private String brevoApiUrl;

    @Value("${brevo.newsletter-list-id:2}")
    private Integer newsletterListId;

    private static final String CONTACTS_ENDPOINT = "/contacts";

    /**
     * Crée ou met à jour un contact dans Brevo
     *
     * @param email Email du contact
     * @param firstName Prénom du contact
     * @param lastName Nom du contact
     * @return BrevoContactResponse avec l'ID du contact créé
     */
    public BrevoContactResponse createOrUpdateContact(String email, String firstName, String lastName) {
        log.info("Creating/updating contact in Brevo: {}", email);

        try {
            // Préparer les attributs
            Map<String, Object> attributes = new HashMap<>();
            if (firstName != null && !firstName.isEmpty()) {
                attributes.put("FNAME", firstName);
            }
            if (lastName != null && !lastName.isEmpty()) {
                attributes.put("LNAME", lastName);
            }

            // Créer la requête Brevo
            BrevoContactRequest request = BrevoContactRequest.builder()
                .email(email)
                .listIds(Arrays.asList(newsletterListId))
                .attributes(attributes)
                .updateEnabled(true)
                .emailBlacklisted(false)
                .smsBlacklisted(false)
                .build();

            // Préparer les headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", brevoApiKey);

            // Faire l'appel API
            HttpEntity<BrevoContactRequest> entity = new HttpEntity<>(request, headers);
            String url = brevoApiUrl + CONTACTS_ENDPOINT;

            log.debug("Calling Brevo API: {} with email: {}", url, email);

            BrevoContactResponse response = restTemplate.postForObject(
                url,
                entity,
                BrevoContactResponse.class
            );

            log.info("Contact created/updated successfully in Brevo: {} (ID: {})", email, response != null ? response.getId() : "N/A");

            return response;

        } catch (RestClientException e) {
            log.error("Error creating/updating contact in Brevo: {}", email, e);
            throw new RuntimeException("Failed to create contact in Brevo: " + e.getMessage(), e);
        }
    }

    /**
     * Vérifie que l'API key est configurée
     */
    public boolean isConfigured() {
        boolean configured = brevoApiKey != null && !brevoApiKey.isEmpty() && !brevoApiKey.equals("your-api-key-here");
        if (!configured) {
            log.warn("Brevo API key not configured. Set BREVO_API_KEY environment variable.");
        }
        return configured;
    }
}

