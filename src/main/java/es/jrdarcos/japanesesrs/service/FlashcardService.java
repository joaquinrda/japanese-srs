package es.jrdarcos.japanesesrs.service;


import es.jrdarcos.japanesesrs.entity.Flashcard;
import es.jrdarcos.japanesesrs.entity.User;
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
    public Flashcard createFlashcard(Flashcard flashcard, User user) {
        flashcard.setNextReview(LocalDate.now());
        flashcard.setEasiness(2.5);
        flashcard.setRepetition(0);
        flashcard.setIntervalDays(0);
        flashcard.setUser(user);

        return flashcardRepository.save(flashcard);
    }

    @Transactional(readOnly = true)
    public List<Flashcard> getAllFlashcards(User user) {
        return flashcardRepository.findByUser(user);
    }

    public List<Flashcard> getDueFlashCards(User user) {
        return flashcardRepository.findByUserAndNextReviewLessThanEqual(user, LocalDate.now());
    }

    @Transactional
    public void deleteFlashcard(Long id) {
        flashcardRepository.deleteById(id);
    }

    @Transactional
    public Flashcard reviewFlashcard(Long id, int quality) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));

        int repetition = flashcard.getRepetition();
        double easiness = flashcard.getEasiness();
        int interval = flashcard.getIntervalDays();

        // SM-2 algorithm
        if (quality >= 3) {

            if (repetition == 0)
                interval = 1;
            else if (repetition == 1)
                interval = 6;
            else
                interval = (int) Math.round(interval * easiness);

            repetition++;
        } else {
            repetition = 0;
            interval = 1;
        }

        easiness = easiness + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02));

        if (easiness < 1.3)
            easiness = 1.3;


        flashcard.setRepetition(repetition);
        flashcard.setEasiness(easiness);
        flashcard.setIntervalDays(interval);

        flashcard.setNextReview(LocalDate.now().plusDays(interval));

        return flashcardRepository.save(flashcard);
    }
}