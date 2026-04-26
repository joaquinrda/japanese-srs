package es.jrdarcos.japanesesrs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "flashcards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String kanji; // Carácter (ej. 猫)

    @NotBlank
    @Column(nullable = false)
    private String reading; // Lectura en kana (ej. ねこ)

    @NotBlank
    @Column(nullable = false)
    private String meaning; // Significado (ej. Gato)

    // --- Variables del Algoritmo de Repaso Espaciado (SRS) ---

    @FutureOrPresent
    private LocalDate nextReview;

    @DecimalMin("1.3")
    @Column(nullable = false)
    private Double easiness = 2.5; // El multiplicador de facilidad (SM-2 empieza en 2.5)

    @PositiveOrZero
    @Column(nullable = false)
    private Integer repetition = 0; // Rachas de aciertos

    @PositiveOrZero
    @Column(nullable = false)
    private Integer intervalDays = 0; // Días hasta el próximo repaso
}