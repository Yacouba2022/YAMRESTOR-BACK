package yamrestor.restor.dto.salle;

import yamrestor.restor.enums.Priorite;
import yamrestor.restor.enums.StatutCommande;
import yamrestor.restor.enums.StatutPreparation;
import yamrestor.restor.enums.TypeCommande;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CommandeDTO {
    private String guid;
    private String numero;
    private TypeCommande type;
    private String tableGuid;
    private String tableNumero;
    private String serveurGuid;
    private String serveurNom;
    private String clientNom;
    private String clientTelephone;
    private StatutCommande statut;
    private Priorite priorite;
    private String observations;
    private BigDecimal montantTotal;
    private LocalDateTime createdAt;
    private List<LigneDTO> lignes;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LigneDTO {
        private String guid;
        private String produitGuid;
        private String produitNom;
        private Integer quantite;
        private BigDecimal prixUnitaire;
        private BigDecimal montantLigne;
        private String notes;
        private StatutPreparation statutPreparation;
    }
}
