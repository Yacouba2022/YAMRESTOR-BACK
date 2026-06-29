package yamrestor.restor.dto.rapport;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Synthèse des ventes sur une période. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VentesRapportDTO {
    private LocalDate debut;
    private LocalDate fin;
    private long nbCommandes;
    private BigDecimal chiffreAffaires;
    private BigDecimal tvaCollectee;
}
