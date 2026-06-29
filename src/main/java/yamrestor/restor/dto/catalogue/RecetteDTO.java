package yamrestor.restor.dto.catalogue;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecetteDTO {
    private String guid;
    private String produitGuid;
    private String produitNom;
    private BigDecimal prixVente;
    private Integer tempsPreparation;
    private String instructions;
    /** Somme des coûts des lignes (quantité × prix d'achat de la matière). */
    private BigDecimal coutRevient;
    /** prixVente − coutRevient (null si prix de vente inconnu). */
    private BigDecimal marge;
    private List<LigneDTO> lignes;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LigneDTO {
        private String guid;
        private String matierePremiereGuid;
        private String matierePremiereNom;
        private BigDecimal quantite;
        private String uniteGuid;
        private String uniteNom;
        private BigDecimal prixAchatUnitaire;
        private BigDecimal coutLigne;
    }
}
