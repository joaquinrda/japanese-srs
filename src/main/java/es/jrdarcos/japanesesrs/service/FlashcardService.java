package es.jrdarcos.japanesesrs.service;


import es.jrdarcos.japanesesrs.entity.Flashcard;
import es.jrdarcos.japanesesrs.repository.FlashcardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;

    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    @Transactional
    public Flashcard createFlashcard(Flashcard flashcard) {
        flashcard.setNextReview(LocalDate.now());
        flashcard.setEasiness(2.5);
        flashcard.setRepetition(0);
        flashcard.setIntervalDays(0);

        return flashcardRepository.save(flashcard);
    }

    @Transactional(readOnly = true)
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    @Transactional
    public void deleteFlashcard(Long id) {
        flashcardRepository.deleteById(id);
    }
}