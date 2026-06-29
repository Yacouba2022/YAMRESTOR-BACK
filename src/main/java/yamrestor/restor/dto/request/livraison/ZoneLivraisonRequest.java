package yamrestor.restor.dto.request.livraison;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ZoneLivraisonRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @DecimalMin(value = "0.0", message = "Les frais doivent être positifs ou nuls")
    private BigDecimal frais;

    private Boolean actif = true;
}
