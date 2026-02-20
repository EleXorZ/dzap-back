package dzap.application.dto;

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

    private String firstName;

    private String lastName;
}

