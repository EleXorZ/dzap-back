package dzap.application.service;

import dzap.application.dto.quiz.*;
import dzap.application.dto.quiz.QuizQuestionDTO.QuizAnswerDTO;
import dzap.application.dto.quiz.QuizQuestionsResponseDTO.QuizInfoDTO;
import dzap.application.dto.quiz.QuizResultResponseDTO.ProfileDTO;
import dzap.application.dto.quiz.QuizResultResponseDTO.StrategyDTO;
import dzap.domain.entity.*;
import dzap.domain.repository.QuizAnswerRepository;
import dzap.domain.repository.QuizQuestionRepository;
import dzap.domain.repository.QuizResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service pour la gestion du quizz investisseur immobilier
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizQuestionRepository questionRepository;
    private final QuizAnswerRepository answerRepository;
    private final QuizResultRepository resultRepository;
    private final BrevoService brevoService;

    private static final String QUIZ_TITLE = "Quel investisseur immobilier êtes-vous ?";
    private static final String QUIZ_DESCRIPTION = "Découvrez votre profil d'investisseur et les stratégies " +
            "d'investissement immobilier les plus adaptées à votre situation.";
    private static final int ESTIMATED_TIME_MINUTES = 3;

    // IDs des listes Brevo par profil d'investisseur
    private static final Map<InvestorProfile, Integer> BREVO_LIST_IDS = Map.of(
            InvestorProfile.PRUDENT, 9,
            InvestorProfile.BALANCED, 10,
            InvestorProfile.DYNAMIC, 11,
            InvestorProfile.PATRIMONIAL, 12
    );

    /**
     * Récupère toutes les questions actives du quizz
     */
    @Transactional(readOnly = true)
    public QuizQuestionsResponseDTO getQuizQuestions() {
        log.info("Fetching active quiz questions");

        List<QuizQuestion> questions = questionRepository.findAllActiveWithAnswers();

        List<QuizQuestionDTO> questionDTOs = questions.stream()
                .map(this::mapQuestionToDTO)
                .collect(Collectors.toList());

        return QuizQuestionsResponseDTO.builder()
                .questions(questionDTOs)
                .totalQuestions(questionDTOs.size())
                .info(QuizInfoDTO.builder()
                        .title(QUIZ_TITLE)
                        .description(QUIZ_DESCRIPTION)
                        .estimatedTimeMinutes(ESTIMATED_TIME_MINUTES)
                        .build())
                .build();
    }

    /**
     * Soumet les réponses et calcule le résultat
     */
    @Transactional
    public QuizResultResponseDTO submitQuiz(QuizSubmitRequestDTO request) {
        log.info("Processing quiz submission for email: {}", request.getEmail());

        // Valider les réponses
        validateAnswers(request.getAnswerIds());

        // Récupérer les réponses avec leurs scores
        List<QuizAnswer> selectedAnswers = answerRepository.findAllByIdWithScores(request.getAnswerIds());

        // Calculer les scores par profil
        Map<InvestorProfile, Integer> scores = calculateScores(selectedAnswers);

        // Déterminer le profil dominant
        InvestorProfile calculatedProfile = determineProfile(scores);

        // Récupérer les stratégies recommandées
        List<InvestmentStrategy> recommendedStrategies = getRecommendedStrategies(calculatedProfile, scores);

        // Sauvegarder le résultat
        QuizResult result = QuizResult.builder()
                .participantEmail(request.getEmail())
                .participantFirstName(request.getFirstName())
                .participantLastName(request.getLastName())
                .calculatedProfile(calculatedProfile)
                .recommendedStrategies(recommendedStrategies)
                .totalScores(scores)
                .selectedAnswerIds(request.getAnswerIds())
                .subscribedNewsletter(Boolean.TRUE.equals(request.getSubscribeNewsletter()))
                .build();

        result = resultRepository.save(result);
        log.info("Quiz result saved with ID: {} for profile: {}", result.getId(), calculatedProfile);

        // Ajouter le contact dans Brevo (liste du profil + newsletter si demandé)
        subscribeToBrevoLists(request, calculatedProfile);

        // Construire et retourner la réponse
        return buildResultResponse(result, scores);
    }

    /**
     * Récupère un résultat par son ID
     */
    @Transactional(readOnly = true)
    public Optional<QuizResultResponseDTO> getResultById(Long resultId) {
        return resultRepository.findById(resultId)
                .map(result -> buildResultResponse(result, result.getTotalScores()));
    }

    /**
     * Récupère les résultats paginés (admin)
     */
    @Transactional(readOnly = true)
    public Page<QuizResultResponseDTO> getAllResults(Pageable pageable) {
        return resultRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(result -> buildResultResponse(result, result.getTotalScores()));
    }

    /**
     * Récupère les statistiques du quizz (admin)
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Total de participations
        stats.put("totalParticipations", resultRepository.count());

        // Par profil
        List<Object[]> profileCounts = resultRepository.countByProfile();
        Map<String, Long> byProfile = new HashMap<>();
        for (Object[] row : profileCounts) {
            byProfile.put(((InvestorProfile) row[0]).name(), (Long) row[1]);
        }
        stats.put("byProfile", byProfile);

        // Inscrits newsletter
        stats.put("newsletterSubscribers", resultRepository.countBySubscribedNewsletterTrue());

        return stats;
    }

    // ==================== Méthodes privées ====================

    private QuizQuestionDTO mapQuestionToDTO(QuizQuestion question) {
        List<QuizAnswerDTO> answerDTOs = question.getAnswers().stream()
                .map(answer -> QuizAnswerDTO.builder()
                        .id(answer.getId())
                        .answerText(answer.getAnswerText())
                        .orderIndex(answer.getOrderIndex())
                        .build())
                .collect(Collectors.toList());

        return QuizQuestionDTO.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .orderIndex(question.getOrderIndex())
                .answers(answerDTOs)
                .build();
    }

    private void validateAnswers(List<Long> answerIds) {
        if (answerIds == null || answerIds.isEmpty()) {
            throw new IllegalArgumentException("Les réponses sont obligatoires");
        }

        long expectedQuestions = questionRepository.countByActiveTrue();
        if (answerIds.size() != expectedQuestions) {
            throw new IllegalArgumentException(
                    String.format("Nombre de réponses incorrect. Attendu: %d, Reçu: %d",
                            expectedQuestions, answerIds.size()));
        }

        long foundAnswers = answerRepository.countByIdIn(answerIds);
        if (foundAnswers != answerIds.size()) {
            throw new IllegalArgumentException("Certaines réponses sont invalides");
        }
    }

    private Map<InvestorProfile, Integer> calculateScores(List<QuizAnswer> answers) {
        Map<InvestorProfile, Integer> scores = new EnumMap<>(InvestorProfile.class);

        // Initialiser tous les profils à 0
        for (InvestorProfile profile : InvestorProfile.values()) {
            scores.put(profile, 0);
        }

        // Sommer les scores de chaque réponse
        for (QuizAnswer answer : answers) {
            for (InvestorProfile profile : InvestorProfile.values()) {
                int currentScore = scores.get(profile);
                int answerScore = answer.getScoreForProfile(profile);
                scores.put(profile, currentScore + answerScore);
            }
        }

        log.debug("Calculated scores: {}", scores);
        return scores;
    }

    private InvestorProfile determineProfile(Map<InvestorProfile, Integer> scores) {
        return scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(InvestorProfile.BALANCED); // Profil par défaut
    }

    private List<InvestmentStrategy> getRecommendedStrategies(InvestorProfile profile,
                                                               Map<InvestorProfile, Integer> scores) {
        // Récupérer toutes les stratégies compatibles avec le profil principal
        List<InvestmentStrategy> strategies = new ArrayList<>();

        for (InvestmentStrategy strategy : InvestmentStrategy.values()) {
            if (strategy.isCompatibleWith(profile)) {
                strategies.add(strategy);
            }
        }

        // Si moins de 3 stratégies, ajouter celles du second profil
        if (strategies.size() < 3) {
            InvestorProfile secondProfile = scores.entrySet().stream()
                    .filter(e -> e.getKey() != profile)
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (secondProfile != null) {
                for (InvestmentStrategy strategy : InvestmentStrategy.values()) {
                    if (strategy.isCompatibleWith(secondProfile) && !strategies.contains(strategy)) {
                        strategies.add(strategy);
                        if (strategies.size() >= 3) break;
                    }
                }
            }
        }

        // Limiter à 3 stratégies max
        return strategies.stream().limit(3).collect(Collectors.toList());
    }

    private void subscribeToBrevoLists(QuizSubmitRequestDTO request, InvestorProfile profile) {
        try {
            if (!brevoService.isConfigured()) {
                log.warn("Brevo not configured, skipping contact creation");
                return;
            }

            // Construire la liste des IDs Brevo
            List<Integer> listIds = new ArrayList<>();

            // Toujours ajouter la liste du profil
            Integer profileListId = BREVO_LIST_IDS.get(profile);
            if (profileListId != null) {
                listIds.add(profileListId);
            }

            // Ajouter la newsletter si demandé
            if (Boolean.TRUE.equals(request.getSubscribeNewsletter())) {
                listIds.add(brevoService.getNewsletterListId());
            }

            // Créer/mettre à jour le contact dans Brevo
            brevoService.createOrUpdateContact(
                    request.getEmail(),
                    request.getFirstName(),
                    request.getLastName(),
                    listIds
            );

            log.info("User {} added to Brevo lists: {}", request.getEmail(), listIds);

        } catch (Exception e) {
            log.error("Failed to add user to Brevo lists: {}", request.getEmail(), e);
            // Ne pas faire échouer le quizz si Brevo échoue
        }
    }

    private QuizResultResponseDTO buildResultResponse(QuizResult result, Map<InvestorProfile, Integer> scores) {
        // Calculer les pourcentages
        int totalScore = scores.values().stream().mapToInt(Integer::intValue).sum();
        Map<String, Double> percentages = new HashMap<>();
        Map<String, Integer> scoresMap = new HashMap<>();

        for (Map.Entry<InvestorProfile, Integer> entry : scores.entrySet()) {
            String key = entry.getKey().name();
            scoresMap.put(key, entry.getValue());
            double percentage = totalScore > 0 ? (entry.getValue() * 100.0) / totalScore : 0;
            percentages.put(key, Math.round(percentage * 10) / 10.0);
        }

        // Construire les DTOs des stratégies
        List<StrategyDTO> strategyDTOs = new ArrayList<>();
        int rank = 1;
        for (InvestmentStrategy strategy : result.getRecommendedStrategies()) {
            strategyDTOs.add(StrategyDTO.fromEntity(strategy, rank++));
        }

        return QuizResultResponseDTO.builder()
                .resultKey(result.getResultKey())
                .resultId(result.getId())
                .profile(ProfileDTO.fromEntity(result.getCalculatedProfile()))
                .recommendedStrategies(strategyDTOs)
                .scores(scoresMap)
                .percentages(percentages)
                .email(result.getParticipantEmail())
                .subscribedNewsletter(result.getSubscribedNewsletter())
                .createdAt(result.getCreatedAt())
                .build();
    }
}

