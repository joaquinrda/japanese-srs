package es.jrdarcos.japanesesrs.repository;

import es.jrdarcos.japanesesrs.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByNextReviewLessThanEqual(LocalDate date);
}
