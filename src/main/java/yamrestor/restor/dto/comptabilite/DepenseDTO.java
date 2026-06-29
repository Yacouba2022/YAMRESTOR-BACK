package yamrestor.restor.dto.comptabilite;

import yamrestor.restor.enums.ModePaiement;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DepenseDTO {
    private String guid;
    private String libelle;
    private String categorieGuid;
    private String categorieNom;
    private BigDecimal montant;
    private LocalDate dateDepense;
    private ModePaiement mode;
    private String description;
}
