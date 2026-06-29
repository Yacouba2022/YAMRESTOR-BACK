package yamrestor.restor.dto.approvisionnement;

import yamrestor.restor.enums.StatutCommandeAchat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CommandeAchatDTO {
    private String guid;
    private String numero;
    private String fournisseurGuid;
    private String fournisseurNom;
    private StatutCommandeAchat statut;
    private BigDecimal montantTotal;
    private LocalDate dateReception;
    private String observations;
    private List<LigneDTO> lignes;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LigneDTO {
        private String guid;
        private String matierePremiereGuid;
        private String matierePremiereNom;
        private BigDecimal quantite;
        private BigDecimal prixUnitaire;
        private BigDecimal montantLigne;
    }
}
