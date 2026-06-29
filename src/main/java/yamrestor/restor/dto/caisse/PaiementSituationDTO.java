package yamrestor.restor.dto.caisse;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/** Situation de paiement d'une commande : montant dû, déjà payé, reste à payer. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaiementSituationDTO {
    private String commandeGuid;
    private String commandeNumero;
    private BigDecimal montantCommande;
    private BigDecimal totalPaye;
    private BigDecimal resteAPayer;
    private Boolean soldee;
    private List<PaiementDTO> paiements;
}
