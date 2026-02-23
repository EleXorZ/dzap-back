package dzap.domain.entity;

import lombok.Getter;

/**
 * Stratégies d'investissement immobilier
 */
@Getter
public enum InvestmentStrategy {
    LOCATION_NUE("Location nue classique",
                 "Location traditionnelle avec un bail de 3 ans. Gestion simplifiée, " +
                 "fiscalité des revenus fonciers. Idéal pour débuter.",
                 new InvestorProfile[]{InvestorProfile.PRUDENT, InvestorProfile.BALANCED}),

    LMNP("Location Meublée Non Professionnelle (LMNP)",
         "Location meublée avec avantages fiscaux (amortissement). " +
         "Rendements supérieurs à la location nue, gestion un peu plus active.",
         new InvestorProfile[]{InvestorProfile.BALANCED, InvestorProfile.DYNAMIC}),

    COLOCATION("Colocation",
               "Location à plusieurs locataires. Rendements élevés mais gestion plus intensive. " +
               "Turnover plus important, idéal dans les grandes villes étudiantes.",
               new InvestorProfile[]{InvestorProfile.DYNAMIC, InvestorProfile.BALANCED}),

    AIRBNB("Location courte durée (Airbnb)",
           "Location saisonnière ou touristique. Rendements potentiellement très élevés " +
           "mais gestion très active et réglementation stricte.",
           new InvestorProfile[]{InvestorProfile.DYNAMIC}),

    CLE_EN_MAIN("Investissement locatif clé en main",
                "Achat d'un bien déjà loué ou avec gestion déléguée. " +
                "Moins de rendement mais aucune gestion à prévoir.",
                new InvestorProfile[]{InvestorProfile.PRUDENT, InvestorProfile.PATRIMONIAL}),

    SCPI("SCPI (Pierre Papier)",
         "Investissement indirect via des parts de sociétés immobilières. " +
         "Diversification, mutualisation des risques, aucune gestion.",
         new InvestorProfile[]{InvestorProfile.PRUDENT, InvestorProfile.BALANCED}),

    IMMEUBLE_RAPPORT("Immeuble de rapport",
                     "Achat d'un immeuble entier. Économies d'échelle, " +
                     "rendements élevés mais ticket d'entrée important.",
                     new InvestorProfile[]{InvestorProfile.DYNAMIC, InvestorProfile.PATRIMONIAL}),

    DIVISION("Division / Marchand de biens",
             "Achat, division et revente de biens. Plus-values importantes " +
             "mais expertise requise et fiscalité spécifique.",
             new InvestorProfile[]{InvestorProfile.DYNAMIC}),

    SCI_IR("SCI à l'IR (Impôt sur le Revenu)",
           "Société Civile Immobilière transparente fiscalement. " +
           "Idéale pour la gestion familiale et la transmission progressive.",
           new InvestorProfile[]{InvestorProfile.PATRIMONIAL, InvestorProfile.BALANCED}),

    SCI_IS("SCI à l'IS (Impôt sur les Sociétés)",
           "SCI soumise à l'IS avec possibilité d'amortissement. " +
           "Capitalisation des revenus, optimisation fiscale long terme.",
           new InvestorProfile[]{InvestorProfile.PATRIMONIAL, InvestorProfile.DYNAMIC});

    private final String label;
    private final String description;
    private final InvestorProfile[] compatibleProfiles;

    InvestmentStrategy(String label, String description, InvestorProfile[] compatibleProfiles) {
        this.label = label;
        this.description = description;
        this.compatibleProfiles = compatibleProfiles;
    }

    /**
     * Vérifie si cette stratégie est compatible avec un profil donné
     */
    public boolean isCompatibleWith(InvestorProfile profile) {
        for (InvestorProfile p : compatibleProfiles) {
            if (p == profile) {
                return true;
            }
        }
        return false;
    }
}

