package yamrestor.restor.dto.location;

import yamrestor.restor.enums.StatutContratLocation;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ContratLocationDTO {
    private String guid;
    private String numero;
    private String clientNom;
    private String clientTelephone;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal montantTotal;
    private BigDecimal cautionTotale;
    private BigDecimal montantPaye;
    private StatutContratLocation statut;
    private String observations;
    private List<LigneDTO> lignes;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LigneDTO {
        private String guid;
        private String materielGuid;
        private String materielNom;
        private Integer quantite;
        private BigDecimal prixUnitaire;
        private BigDecimal montantLigne;
        private BigDecimal caution;
    }
}
