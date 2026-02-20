package dzap.application.dto.brevo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la réponse de Brevo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrevoContactResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("message")
    private String message;
}

