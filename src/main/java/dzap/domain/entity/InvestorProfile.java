package dzap.domain.entity;

import lombok.Getter;

/**
 * Profils d'investisseur immobilier
 */
@Getter
public enum InvestorProfile {
    PRUDENT("Prudent / Sécuritaire",
            "Vous privilégiez la sécurité et la stabilité dans vos investissements. " +
            "Vous préférez des rendements modérés mais constants, avec peu de risques. " +
            "La préservation du capital est votre priorité."),

    BALANCED("Équilibré",
             "Vous recherchez un équilibre entre sécurité et rendement. " +
             "Vous êtes prêt à accepter un niveau de risque modéré pour obtenir de meilleurs rendements. " +
             "La diversification est au cœur de votre stratégie."),

    DYNAMIC("Dynamique / Offensif",
            "Vous êtes prêt à prendre des risques pour maximiser vos rendements. " +
            "Vous avez une vision long terme et pouvez supporter des fluctuations. " +
            "Vous recherchez activement les opportunités à fort potentiel."),

    PATRIMONIAL("Patrimonial",
                "Vous investissez dans une optique de transmission et d'optimisation fiscale. " +
                "La constitution d'un patrimoine durable pour vos héritiers est votre objectif principal. " +
                "Vous privilégiez les montages juridiques adaptés.");

    private final String label;
    private final String description;

    InvestorProfile(String label, String description) {
        this.label = label;
        this.description = description;
    }
}

