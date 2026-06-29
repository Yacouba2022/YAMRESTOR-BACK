package yamrestor.restor.dto.traiteur;

import yamrestor.restor.enums.StatutDevis;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DevisDTO {
    private String guid;
    private String numero;
    private String prestationGuid;
    private String prestationNumero;
    private BigDecimal montantTotal;
    private StatutDevis statut;
    private String observations;
    private List<LigneDTO> lignes;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LigneDTO {
        private String guid;
        private String designation;
        private String menuTraiteurGuid;
        private String menuTraiteurNom;
        private BigDecimal quantite;
        private BigDecimal prixUnitaire;
        private BigDecimal montantLigne;
    }
}
