package dzap.application.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour s'inscrire à la newsletter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsletterSubscribeDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @JsonProperty("firstName")
    @JsonAlias({"first_name", "firstname", "prenom", "prénom"})
    private String firstName;

    @JsonProperty("lastName")
    @JsonAlias({"last_name", "lastname", "nom"})
    private String lastName;
}

