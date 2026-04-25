package es.jrdarcos.japanesesrs.controller;

import es.jrdarcos.japanesesrs.entity.Flashcard;
import es.jrdarcos.japanesesrs.service.FlashcardService;
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
    public ResponseEntity<Flashcard> create(@RequestBody Flashcard flashcard) {
        return ResponseEntity.ok(flashcardService.createFlashcard(flashcard));
    }

    @GetMapping
    public ResponseEntity<List<Flashcard>> getAll() {
        return ResponseEntity.ok(flashcardService.getAllFlashcards());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
        return ResponseEntity.noContent().build();
    }
}
