package yamrestor.restor.dto.salle;

import yamrestor.restor.enums.Priorite;
import yamrestor.restor.enums.StatutPreparation;
import lombok.*;

import java.time.LocalDateTime;

/** Élément affiché sur l'écran cuisine (Kitchen Display) : une ligne de commande à préparer. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class KdsLigneDTO {
    private String ligneGuid;
    private String commandeGuid;
    private String commandeNumero;
    private String tableNumero;
    private String produitNom;
    private Integer quantite;
    private String notes;
    private StatutPreparation statutPreparation;
    private Priorite priorite;
    private LocalDateTime commandeDepuis;
}
