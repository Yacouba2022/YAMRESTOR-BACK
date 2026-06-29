package yamrestor.restor.dto.traiteur;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/** Synthèse des prestations traiteur sur une période. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RapportTraiteurDTO {
    private LocalDate debut;
    private LocalDate fin;
    private long nbPrestations;
    /** Nombre de prestations par statut (DEVIS, CONFIRMEE, REALISEE, ANNULEE…). */
    private Map<String, Long> parStatut;
    private BigDecimal chiffreAffaires;
    private BigDecimal coutTotal;
    private BigDecimal benefice;
}
