package dzap.application.dto.quiz;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO pour soumettre les réponses du quizz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSubmitRequestDTO {

    /**
     * Liste des IDs des réponses sélectionnées (une par question)
     */
    @NotEmpty(message = "Les réponses sont obligatoires")
    @JsonProperty("answerIds")
    @JsonAlias({"answers", "selectedAnswers", "answer_ids"})
    private List<Long> answerIds;

    /**
     * Email du participant (pour envoyer les résultats)
     */
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    /**
     * Prénom du participant
     */
    @JsonProperty("firstName")
    @JsonAlias({"first_name", "firstname", "prenom", "prénom"})
    private String firstName;

    /**
     * Nom du participant
     */
    @JsonProperty("lastName")
    @JsonAlias({"last_name", "lastname", "nom"})
    private String lastName;

    /**
     * Souhaite s'inscrire à la newsletter en plus
     */
    @JsonProperty("subscribeNewsletter")
    @JsonAlias({"subscribe_newsletter", "newsletter"})
    @Builder.Default
    private Boolean subscribeNewsletter = false;
}

