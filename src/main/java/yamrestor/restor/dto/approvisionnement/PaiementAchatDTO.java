package yamrestor.restor.dto.approvisionnement;

import yamrestor.restor.enums.ModePaiement;
import yamrestor.restor.enums.StatutPaiement;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaiementAchatDTO {
    private String guid;
    private String numero;
    private String fournisseurGuid;
    private String fournisseurNom;
    private String commandeAchatGuid;
    private String commandeAchatNumero;
    private BigDecimal montant;
    private ModePaiement mode;
    private String reference;
    private StatutPaiement statut;
    private LocalDateTime createdAt;
}
