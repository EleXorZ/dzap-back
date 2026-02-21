package dzap.application.dto.brevo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrevoContactRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("updateEnabled")
    @Builder.Default
    private Boolean updateEnabled = true;

    @JsonProperty("attributes")
    @Builder.Default
    private Map<String, String> attributes = new HashMap<>();

    @JsonProperty("listIds")
    private List<Integer> listIds;
}


