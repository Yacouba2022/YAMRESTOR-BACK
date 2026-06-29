package yamrestor.restor.dto.livraison;

import yamrestor.restor.enums.StatutLivraison;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LivraisonDTO {
    private String guid;
    private String commandeGuid;
    private String commandeNumero;
    private String livreurGuid;
    private String livreurNom;
    private String zoneGuid;
    private String zoneNom;
    private BigDecimal frais;
    private String adresse;
    private String telephone;
    private StatutLivraison statut;
    private LocalDateTime dateLivraison;
    private String commentaire;
}
