package dzap.config;

import dzap.domain.entity.InvestorProfile;
import dzap.domain.entity.QuizAnswer;
import dzap.domain.entity.QuizQuestion;
import dzap.domain.repository.QuizQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Configuration pour initialiser les questions du quizz au démarrage
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class QuizDataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initQuizData(QuizQuestionRepository questionRepository) {
        return args -> {
            // Ne pas réinitialiser si des questions existent déjà
            if (questionRepository.count() > 0) {
                log.info("Quiz questions already exist, skipping initialization");
                return;
            }

            log.info("Initializing quiz questions...");

            // Question 1
            createQuestion(questionRepository, 1,
                "Quel est votre objectif principal en investissant dans l'immobilier ?",
                new String[]{
                    "Générer des revenus complémentaires stables et sécurisés",
                    "Constituer un patrimoine équilibré entre rendement et sécurité",
                    "Maximiser mes rendements, quitte à prendre des risques",
                    "Préparer ma succession et optimiser ma fiscalité"
                },
                new int[][]{
                    {3, 1, 0, 1}, // PRUDENT, BALANCED, DYNAMIC, PATRIMONIAL
                    {1, 3, 1, 1},
                    {0, 1, 3, 0},
                    {1, 1, 0, 3}
                }
            );

            // Question 2
            createQuestion(questionRepository, 2,
                "Sur quel horizon de temps envisagez-vous votre investissement ?",
                new String[]{
                    "Court terme (moins de 5 ans)",
                    "Moyen terme (5 à 10 ans)",
                    "Long terme (10 à 20 ans)",
                    "Très long terme (transmission aux enfants)"
                },
                new int[][]{
                    {0, 1, 3, 0},
                    {2, 3, 1, 0},
                    {3, 2, 1, 2},
                    {1, 1, 0, 3}
                }
            );

            // Question 3
            createQuestion(questionRepository, 3,
                "Comment réagiriez-vous si la valeur de votre bien baissait de 15% ?",
                new String[]{
                    "Je serais très inquiet et envisagerais de vendre",
                    "Je serais préoccupé mais je garderais le bien",
                    "C'est temporaire, j'achèterais peut-être un autre bien",
                    "La valeur m'importe peu, je me concentre sur les loyers et la transmission"
                },
                new int[][]{
                    {3, 0, 0, 0},
                    {2, 3, 0, 1},
                    {0, 1, 3, 0},
                    {1, 1, 1, 3}
                }
            );

            // Question 4
            createQuestion(questionRepository, 4,
                "Quel niveau d'implication êtes-vous prêt à avoir dans la gestion ?",
                new String[]{
                    "Aucune, je veux déléguer totalement",
                    "Limitée, je peux superviser une agence",
                    "Active, je veux gérer moi-même pour optimiser",
                    "Je veux structurer un montage familial avec mes proches"
                },
                new int[][]{
                    {3, 1, 0, 1},
                    {1, 3, 1, 1},
                    {0, 1, 3, 0},
                    {0, 1, 1, 3}
                }
            );

            // Question 5
            createQuestion(questionRepository, 5,
                "Quel est votre budget approximatif pour investir ?",
                new String[]{
                    "Moins de 50 000€",
                    "Entre 50 000€ et 150 000€",
                    "Entre 150 000€ et 400 000€",
                    "Plus de 400 000€"
                },
                new int[][]{
                    {2, 2, 1, 0},
                    {2, 3, 2, 1},
                    {1, 2, 3, 2},
                    {0, 1, 2, 3}
                }
            );

            // Question 6
            createQuestion(questionRepository, 6,
                "Quelle est votre expérience en investissement immobilier ?",
                new String[]{
                    "Aucune, c'est mon premier investissement",
                    "J'ai déjà ma résidence principale",
                    "J'ai 1 ou 2 biens locatifs",
                    "J'ai un patrimoine immobilier conséquent (3+ biens)"
                },
                new int[][]{
                    {3, 2, 0, 0},
                    {2, 3, 1, 1},
                    {1, 2, 3, 2},
                    {0, 1, 2, 3}
                }
            );

            // Question 7
            createQuestion(questionRepository, 7,
                "Quel type de rendement vous attire le plus ?",
                new String[]{
                    "Rendement modéré (3-5%) mais très sécurisé",
                    "Rendement correct (5-7%) avec une gestion raisonnable",
                    "Rendement élevé (8%+) même si cela demande plus d'efforts",
                    "Le rendement immédiat m'importe peu, je vise les avantages fiscaux"
                },
                new int[][]{
                    {3, 1, 0, 1},
                    {1, 3, 1, 1},
                    {0, 1, 3, 0},
                    {1, 1, 0, 3}
                }
            );

            // Question 8
            createQuestion(questionRepository, 8,
                "Comment décririez-vous votre situation fiscale actuelle ?",
                new String[]{
                    "TMI faible (0-11%), je cherche surtout à me constituer un patrimoine",
                    "TMI moyen (30%), je veux équilibrer rendement et fiscalité",
                    "TMI élevé (41%+), l'optimisation fiscale est importante",
                    "Je suis soumis à l'IFI et je cherche à optimiser ma transmission"
                },
                new int[][]{
                    {2, 2, 2, 0},
                    {1, 3, 2, 1},
                    {0, 1, 2, 3},
                    {0, 0, 1, 3}
                }
            );

            // Question 9
            createQuestion(questionRepository, 9,
                "Quel type de bien vous attire le plus ?",
                new String[]{
                    "Un appartement dans une grande ville, facile à louer",
                    "Un bien avec travaux pour créer de la valeur",
                    "Un immeuble de rapport pour maximiser le rendement",
                    "Un bien de prestige ou familial à transmettre"
                },
                new int[][]{
                    {3, 2, 0, 1},
                    {0, 2, 3, 0},
                    {0, 1, 3, 1},
                    {1, 1, 0, 3}
                }
            );

            // Question 10
            createQuestion(questionRepository, 10,
                "Comment envisagez-vous de financer votre investissement ?",
                new String[]{
                    "Plutôt comptant ou avec un faible emprunt",
                    "Un emprunt classique avec un bon apport (20-30%)",
                    "Maximum d'effet de levier, emprunt à 110%",
                    "Via une structure (SCI) pour optimiser la transmission"
                },
                new int[][]{
                    {3, 1, 0, 1},
                    {2, 3, 1, 1},
                    {0, 1, 3, 0},
                    {0, 1, 1, 3}
                }
            );

            log.info("Quiz questions initialized successfully - {} questions created", 
                questionRepository.count());
        };
    }

    private void createQuestion(QuizQuestionRepository repository, int orderIndex, 
                                String questionText, String[] answers, int[][] scores) {
        QuizQuestion question = QuizQuestion.builder()
                .questionText(questionText)
                .orderIndex(orderIndex)
                .active(true)
                .build();

        for (int i = 0; i < answers.length; i++) {
            QuizAnswer answer = QuizAnswer.builder()
                    .answerText(answers[i])
                    .orderIndex(i + 1)
                    .profileScores(Map.of(
                            InvestorProfile.PRUDENT, scores[i][0],
                            InvestorProfile.BALANCED, scores[i][1],
                            InvestorProfile.DYNAMIC, scores[i][2],
                            InvestorProfile.PATRIMONIAL, scores[i][3]
                    ))
                    .build();
            question.addAnswer(answer);
        }

        repository.save(question);
    }
}

