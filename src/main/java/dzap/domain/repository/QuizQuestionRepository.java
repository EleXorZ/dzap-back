package dzap.domain.repository;

import dzap.domain.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour les questions du quizz
 */
@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    /**
     * Récupère toutes les questions actives ordonnées par index
     */
    List<QuizQuestion> findByActiveTrueOrderByOrderIndexAsc();

    /**
     * Récupère toutes les questions avec leurs réponses (fetch join)
     */
    @Query("SELECT DISTINCT q FROM QuizQuestion q LEFT JOIN FETCH q.answers WHERE q.active = true ORDER BY q.orderIndex ASC")
    List<QuizQuestion> findAllActiveWithAnswers();

    /**
     * Compte le nombre de questions actives
     */
    long countByActiveTrue();

    /**
     * Vérifie si une question existe avec cet index
     */
    boolean existsByOrderIndex(Integer orderIndex);
}

