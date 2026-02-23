package dzap.application.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO représentant une question du quizz avec ses réponses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionDTO {

    private Long id;
    private String questionText;
    private Integer orderIndex;
    private List<QuizAnswerDTO> answers;

    /**
     * DTO pour une réponse possible
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuizAnswerDTO {
        private Long id;
        private String answerText;
        private Integer orderIndex;
    }
}

