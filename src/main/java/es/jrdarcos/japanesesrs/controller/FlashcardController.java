package es.jrdarcos.japanesesrs.controller;

import es.jrdarcos.japanesesrs.entity.Flashcard;
import es.jrdarcos.japanesesrs.entity.User;
import es.jrdarcos.japanesesrs.service.FlashcardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(flashcardService.createFlashcard(flashcard, user));
    }

    @GetMapping
    public ResponseEntity<List<Flashcard>> getAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(flashcardService.getAllFlashcards(user));
    }

    @GetMapping("/due")
    public ResponseEntity<List<Flashcard>> getDue() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(flashcardService.getDueFlashCards(user));
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
