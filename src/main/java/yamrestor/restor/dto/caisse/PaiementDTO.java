package yamrestor.restor.dto.caisse;

import yamrestor.restor.enums.ModePaiement;
import yamrestor.restor.enums.StatutPaiement;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaiementDTO {
    private String guid;
    private String numero;
    private String commandeGuid;
    private String commandeNumero;
    private String sessionCaisseGuid;
    private BigDecimal montant;
    private BigDecimal rendu;
    private StatutPaiement statut;
    private LocalDateTime createdAt;
    private List<LigneDTO> lignes;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LigneDTO {
        private String guid;
        private ModePaiement mode;
        private BigDecimal montant;
        private String reference;
    }
}
