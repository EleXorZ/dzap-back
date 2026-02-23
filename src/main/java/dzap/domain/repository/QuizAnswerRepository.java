package dzap.domain.repository;

import dzap.domain.entity.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour les réponses du quizz
 */
@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {

    /**
     * Récupère toutes les réponses pour une question donnée
     */
    List<QuizAnswer> findByQuestionIdOrderByOrderIndexAsc(Long questionId);

    /**
     * Récupère plusieurs réponses par leurs IDs avec leurs scores
     */
    @Query("SELECT a FROM QuizAnswer a LEFT JOIN FETCH a.profileScores WHERE a.id IN :ids")
    List<QuizAnswer> findAllByIdWithScores(@Param("ids") List<Long> ids);

    /**
     * Vérifie que toutes les réponses existent
     */
    @Query("SELECT COUNT(a) FROM QuizAnswer a WHERE a.id IN :ids")
    long countByIdIn(@Param("ids") List<Long> ids);
}

