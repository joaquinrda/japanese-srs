package es.jrdarcos.japanesesrs.service;

import es.jrdarcos.japanesesrs.entity.Flashcard;
import es.jrdarcos.japanesesrs.repository.FlashcardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlashcardServiceTest {

    @Mock
    private FlashcardRepository repository;

    @InjectMocks
    private FlashcardService service;

    @Test
    void shouldSetInitialReviewDateWhenCreatingFlashcard() {
        Flashcard cardIn = Flashcard.builder()
                .kanji("猫")
                .reading("ねこ")
                .meaning("Gato")
                .build();

        when(repository.save(any(Flashcard.class))).thenAnswer(i -> i.getArguments()[0]);

        Flashcard cardSaved = service.createFlashcard(cardIn, null);

        assertThat(cardSaved.getNextReview()).isEqualTo(LocalDate.now());
        assertThat(cardSaved.getEasiness()).isEqualTo(2.5);
        assertThat(cardSaved.getRepetition()).isEqualTo(0);
        assertThat(cardSaved.getIntervalDays()).isEqualTo(0);
    }
}