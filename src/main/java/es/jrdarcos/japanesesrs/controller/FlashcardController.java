package es.jrdarcos.japanesesrs.controller;

import es.jrdarcos.japanesesrs.entity.Flashcard;
import es.jrdarcos.japanesesrs.service.FlashcardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "http://localhost:4200")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    public ResponseEntity<Flashcard> create(@Valid @RequestBody Flashcard flashcard) {
        return ResponseEntity.ok(flashcardService.createFlashcard(flashcard));
    }

    @GetMapping
    public ResponseEntity<List<Flashcard>> getAll() {
        return ResponseEntity.ok(flashcardService.getAllFlashcards());
    }

    @GetMapping("/due")
    public ResponseEntity<List<Flashcard>> getDue() {
        return ResponseEntity.ok(flashcardService.getDueFlashCards());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<Flashcard> review(
            @NotNull @PathVariable Long id,
            @NotNull @Min(0) @Max(5) @RequestParam int quality) {

        return ResponseEntity.ok(flashcardService.reviewFlashcard(id, quality));
    }
}
