package dzap.application.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO pour la réponse contenant toutes les questions du quizz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionsResponseDTO {

    /**
     * Liste des questions avec leurs réponses
     */
    private List<QuizQuestionDTO> questions;

    /**
     * Nombre total de questions
     */
    private Integer totalQuestions;

    /**
     * Informations sur le quizz
     */
    private QuizInfoDTO info;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuizInfoDTO {
        private String title;
        private String description;
        private Integer estimatedTimeMinutes;
    }
}

