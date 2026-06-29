package yamrestor.restor.dto.comptabilite;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Compte de résultat simplifié sur une période : recettes − dépenses = bénéfice. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompteResultatDTO {
    private LocalDate debut;
    private LocalDate fin;
    private BigDecimal totalRecettes;
    private BigDecimal totalDepenses;
    private BigDecimal benefice;
}
