package dzap.domain.repository;

import dzap.domain.entity.InvestorProfile;
import dzap.domain.entity.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour les résultats du quizz
 */
@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    /**
     * Trouve tous les résultats pour un email donné
     */
    List<QuizResult> findByParticipantEmailOrderByCreatedAtDesc(String email);

    /**
     * Trouve le dernier résultat pour un email
     */
    Optional<QuizResult> findFirstByParticipantEmailOrderByCreatedAtDesc(String email);

    /**
     * Compte les résultats par profil
     */
    long countByCalculatedProfile(InvestorProfile profile);

    /**
     * Récupère les résultats paginés
     */
    Page<QuizResult> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Récupère les résultats dans une période
     */
    List<QuizResult> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Statistiques par profil
     */
    @Query("SELECT r.calculatedProfile, COUNT(r) FROM QuizResult r GROUP BY r.calculatedProfile")
    List<Object[]> countByProfile();

    /**
     * Compte le nombre d'inscrits à la newsletter via le quizz
     */
    long countBySubscribedNewsletterTrue();

    /**
     * Recherche par email partiel
     */
    Page<QuizResult> findByParticipantEmailContainingIgnoreCase(String email, Pageable pageable);
}

