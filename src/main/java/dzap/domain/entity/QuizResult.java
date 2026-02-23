package dzap.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entité représentant le résultat d'un quizz complété
 */
@Entity
@Table(name = "quiz_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "participant_email", nullable = false)
    private String participantEmail;

    @Column(name = "participant_first_name")
    private String participantFirstName;

    @Column(name = "participant_last_name")
    private String participantLastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "calculated_profile", nullable = false)
    private InvestorProfile calculatedProfile;

    /**
     * Liste des stratégies recommandées (ordonnées par pertinence)
     */
    @ElementCollection
    @CollectionTable(name = "quiz_result_strategies", joinColumns = @JoinColumn(name = "result_id"))
    @Column(name = "strategy")
    @Enumerated(EnumType.STRING)
    @OrderColumn(name = "rank")
    @Builder.Default
    private List<InvestmentStrategy> recommendedStrategies = new ArrayList<>();

    /**
     * Scores totaux par profil
     */
    @ElementCollection
    @CollectionTable(name = "quiz_result_scores", joinColumns = @JoinColumn(name = "result_id"))
    @MapKeyColumn(name = "profile")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "score")
    @Builder.Default
    private Map<InvestorProfile, Integer> totalScores = new HashMap<>();

    /**
     * IDs des réponses sélectionnées (pour traçabilité)
     */
    @ElementCollection
    @CollectionTable(name = "quiz_result_answers", joinColumns = @JoinColumn(name = "result_id"))
    @Column(name = "answer_id")
    @Builder.Default
    private List<Long> selectedAnswerIds = new ArrayList<>();

    @Column(name = "subscribed_newsletter", nullable = false)
    @Builder.Default
    private Boolean subscribedNewsletter = false;

    @Column(name = "email_sent", nullable = false)
    @Builder.Default
    private Boolean emailSent = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Génère un identifiant unique pour ce résultat (pour partage/consultation)
     */
    public String getResultKey() {
        return "QR-" + id + "-" + calculatedProfile.name().substring(0, 3);
    }
}

