package es.jrdarcos.japanesesrs.repository;

import es.jrdarcos.japanesesrs.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}
