package yamrestor.restor.dto.traiteur;

import yamrestor.restor.enums.ModePaiement;
import yamrestor.restor.enums.StatutPaiement;
import yamrestor.restor.enums.TypePaiementPrestation;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaiementPrestationDTO {
    private String guid;
    private String numero;
    private String prestationGuid;
    private String prestationNumero;
    private BigDecimal montant;
    private ModePaiement mode;
    private TypePaiementPrestation type;
    private String reference;
    private StatutPaiement statut;
    private LocalDateTime createdAt;
}
