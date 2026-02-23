package dzap.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Entité représentant une réponse possible à une question du quizz
 */
@Entity
@Table(name = "quiz_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private QuizQuestion question;

    @Column(name = "answer_text", nullable = false, length = 500)
    private String answerText;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    /**
     * Scores par profil d'investisseur pour cette réponse
     * Stocké en JSON dans PostgreSQL
     * Ex: {"PRUDENT": 2, "BALANCED": 1, "DYNAMIC": 0, "PATRIMONIAL": 1}
     */
    @ElementCollection
    @CollectionTable(name = "quiz_answer_scores", joinColumns = @JoinColumn(name = "answer_id"))
    @MapKeyColumn(name = "profile")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "score")
    @Builder.Default
    private Map<InvestorProfile, Integer> profileScores = new HashMap<>();

    /**
     * Récupère le score pour un profil donné
     */
    public int getScoreForProfile(InvestorProfile profile) {
        return profileScores.getOrDefault(profile, 0);
    }

    /**
     * Définit le score pour un profil donné
     */
    public void setScoreForProfile(InvestorProfile profile, int score) {
        profileScores.put(profile, score);
    }
}

