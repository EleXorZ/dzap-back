package dzap.presentation.controller;

import dzap.application.dto.quiz.QuizQuestionsResponseDTO;
import dzap.application.dto.quiz.QuizResultResponseDTO;
import dzap.application.dto.quiz.QuizSubmitRequestDTO;
import dzap.application.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur REST pour le quizz investisseur immobilier
 */
@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Quiz API", description = "Endpoints pour le quizz profil investisseur immobilier")
public class QuizController {

    private final QuizService quizService;

    /**
     * Récupère les questions du quizz
     */
    @GetMapping("/questions")
    @Operation(
            summary = "Récupérer les questions du quizz",
            description = "Retourne toutes les questions actives avec leurs réponses possibles"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Questions récupérées avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<QuizQuestionsResponseDTO> getQuestions() {
        log.info("GET /api/quiz/questions - Fetching quiz questions");

        try {
            QuizQuestionsResponseDTO response = quizService.getQuizQuestions();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching quiz questions", e);
            throw new RuntimeException("Erreur lors de la récupération des questions", e);
        }
    }

    /**
     * Soumet les réponses et obtient le résultat
     */
    @PostMapping("/submit")
    @Operation(
            summary = "Soumettre le quizz",
            description = "Envoie les réponses, calcule le profil et retourne les résultats avec les stratégies recommandées"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quizz traité avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<?> submitQuiz(@Valid @RequestBody QuizSubmitRequestDTO request) {
        log.info("POST /api/quiz/submit - Processing quiz for email: {}", request.getEmail());

        try {
            QuizResultResponseDTO result = quizService.submitQuiz(request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid quiz submission: {}", e.getMessage());
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing quiz submission", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erreur lors du traitement du quizz"));
        }
    }

    /**
     * Récupère un résultat par son ID
     */
    @GetMapping("/result/{resultId}")
    @Operation(
            summary = "Récupérer un résultat",
            description = "Récupère les détails d'un résultat de quizz par son ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Résultat trouvé"),
            @ApiResponse(responseCode = "404", description = "Résultat non trouvé")
    })
    public ResponseEntity<?> getResult(
            @Parameter(description = "ID du résultat") @PathVariable Long resultId) {
        log.info("GET /api/quiz/result/{} - Fetching result", resultId);

        return quizService.getResultById(resultId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Healthcheck du service quizz
     */
    @GetMapping("/health")
    @Operation(summary = "Vérifier le statut du service quizz")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Quiz Investisseur Immobilier");
        return ResponseEntity.ok(response);
    }

    // ==================== Admin endpoints ====================

    /**
     * Récupère tous les résultats (admin)
     */
    @GetMapping("/admin/results")
    @Operation(summary = "Récupérer tous les résultats (Admin)")
    public ResponseEntity<Page<QuizResultResponseDTO>> getAllResults(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("GET /api/quiz/admin/results - Fetching all results (page: {}, size: {})", page, size);

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(quizService.getAllResults(pageable));
    }

    /**
     * Récupère les statistiques du quizz (admin)
     */
    @GetMapping("/admin/statistics")
    @Operation(summary = "Récupérer les statistiques du quizz (Admin)")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        log.info("GET /api/quiz/admin/statistics - Fetching statistics");
        return ResponseEntity.ok(quizService.getStatistics());
    }

    // ==================== Helper methods ====================

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("error", message);
        return error;
    }
}

