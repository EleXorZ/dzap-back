package dzap.presentation.controller;

import dzap.application.dto.NewsletterSubscribeDTO;
import dzap.application.dto.brevo.BrevoContactResponse;
import dzap.application.service.BrevoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur REST pour l'intégration de la newsletter
 */
@RestController
@RequestMapping("/api/newsletter")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Newsletter API", description = "Endpoints pour la gestion de la newsletter")
public class NewsletterController {

    private final BrevoService brevoService;

    /**
     * S'inscrire à la newsletter via Brevo
     *
     * @param request Données du formulaire newsletter
     * @return Réponse avec ID du contact créé
     */
    @PostMapping("/subscribe")
    @Operation(summary = "S'inscrire à la newsletter", description = "Ajoute l'email à la liste de newsletter Brevo")
    public ResponseEntity<Map<String, Object>> subscribe(@Valid @RequestBody NewsletterSubscribeDTO request) {
        log.info("Newsletter subscription request for email: {}", request.getEmail());

        try {
            // Vérifier que Brevo est configuré
            if (!brevoService.isConfigured()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(createErrorResponse("Newsletter service is not configured"));
            }

            // Créer/mettre à jour le contact dans Brevo
            BrevoContactResponse response = brevoService.createOrUpdateContact(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName()
            );

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "You have been successfully subscribed to our newsletter");
            result.put("contactId", response != null ? response.getId() : null);
            result.put("email", request.getEmail());

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("Error subscribing to newsletter: {}", request.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Failed to subscribe to newsletter: " + e.getMessage()));
        }
    }

    /**
     * Healthcheck pour la newsletter
     */
    @GetMapping("/health")
    @Operation(summary = "Vérifier que le service newsletter est disponible")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", brevoService.isConfigured() ? "UP" : "DOWN");
        response.put("service", "Brevo Newsletter");

        return ResponseEntity.ok(response);
    }

    /**
     * Crée une réponse d'erreur standardisée
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        return error;
    }
}

