package es.jrdarcos.japanesesrs.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String kanji; // Carácter (ej. 猫)

    @Column(nullable = false)
    private String reading; // Lectura en kana (ej. ねこ)

    @Column(nullable = false)
    private String meaning; // Significado (ej. Gato)

    // --- Variables del Algoritmo de Repaso Espaciado (SRS) ---

    private LocalDate nextReview;

    @Column(nullable = false)
    private Double easiness = 2.5; // El multiplicador de facilidad (SM-2 empieza en 2.5)

    @Column(nullable = false)
    private Integer repetition = 0; // Rachas de aciertos

    @Column(nullable = false)
    private Integer intervalDays = 0; // Días hasta el próximo repaso
}