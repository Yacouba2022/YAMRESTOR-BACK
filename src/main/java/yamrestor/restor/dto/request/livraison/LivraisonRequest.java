package yamrestor.restor.dto.request.livraison;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class LivraisonRequest {

    @NotBlank(message = "La commande est obligatoire")
    private String commandeGuid;

    private String livreurGuid;
    private String zoneGuid;

    /** Frais de livraison ; si absent, repris de la zone. */
    @DecimalMin(value = "0.0", message = "Les frais doivent être positifs ou nuls")
    private BigDecimal frais;

    private String adresse;
    private String telephone;
    private String commentaire;
}
