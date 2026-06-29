package yamrestor.restor.dto.traiteur;

import yamrestor.restor.enums.StatutContrat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ContratDTO {
    private String guid;
    private String numero;
    private String prestationGuid;
    private String prestationNumero;
    private String devisGuid;
    private BigDecimal montant;
    private String conditions;
    private LocalDate dateSignature;
    private StatutContrat statut;
}
