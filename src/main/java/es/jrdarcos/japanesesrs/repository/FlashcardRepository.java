package es.jrdarcos.japanesesrs.repository;

import es.jrdarcos.japanesesrs.entity.Flashcard;
import es.jrdarcos.japanesesrs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByUser(User user);
    List<Flashcard> findByUserAndNextReviewLessThanEqual(User user, LocalDate date);
}
