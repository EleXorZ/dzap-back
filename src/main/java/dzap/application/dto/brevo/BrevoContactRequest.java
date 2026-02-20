package dzap.application.dto.brevo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * DTO pour créer un contact dans Brevo
 * Basé sur : https://developers.brevo.com/docs/synchronise-contact-lists#create-a-contact
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrevoContactRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("listIds")
    private List<Integer> listIds;

    @JsonProperty("attributes")
    private Map<String, Object> attributes;

    @JsonProperty("updateEnabled")
    @Builder.Default
    private Boolean updateEnabled = true;

    @JsonProperty("emailBlacklisted")
    @Builder.Default
    private Boolean emailBlacklisted = false;

    @JsonProperty("smsBlacklisted")
    @Builder.Default
    private Boolean smsBlacklisted = false;
}


