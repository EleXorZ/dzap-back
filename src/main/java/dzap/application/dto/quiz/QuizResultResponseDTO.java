package dzap.application.dto.quiz;

import dzap.domain.entity.InvestorProfile;
import dzap.domain.entity.InvestmentStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * DTO de réponse avec le résultat du quizz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResultResponseDTO {

    /**
     * Identifiant unique du résultat (pour partage/consultation)
     */
    private String resultKey;

    /**
     * ID en base de données
     */
    private Long resultId;

    /**
     * Profil d'investisseur calculé
     */
    private ProfileDTO profile;

    /**
     * Stratégies recommandées (ordonnées par pertinence)
     */
    private List<StrategyDTO> recommendedStrategies;

    /**
     * Scores détaillés par profil (pour affichage graphique)
     */
    private Map<String, Integer> scores;

    /**
     * Pourcentages par profil
     */
    private Map<String, Double> percentages;

    /**
     * Email du participant
     */
    private String email;

    /**
     * Inscrit à la newsletter
     */
    private Boolean subscribedNewsletter;

    /**
     * Date de création
     */
    private LocalDateTime createdAt;

    /**
     * DTO pour le profil d'investisseur
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProfileDTO {
        private String key;
        private String label;
        private String description;

        public static ProfileDTO fromEntity(InvestorProfile profile) {
            return ProfileDTO.builder()
                    .key(profile.name())
                    .label(profile.getLabel())
                    .description(profile.getDescription())
                    .build();
        }
    }

    /**
     * DTO pour une stratégie recommandée
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StrategyDTO {
        private String key;
        private String label;
        private String description;
        private Integer rank;

        public static StrategyDTO fromEntity(InvestmentStrategy strategy, int rank) {
            return StrategyDTO.builder()
                    .key(strategy.name())
                    .label(strategy.getLabel())
                    .description(strategy.getDescription())
                    .rank(rank)
                    .build();
        }
    }
}

