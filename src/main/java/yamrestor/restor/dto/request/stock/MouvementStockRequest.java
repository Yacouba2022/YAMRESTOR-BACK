package yamrestor.restor.dto.request.stock;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** Entrée ou sortie directe de stock. */
@Getter @Setter
public class MouvementStockRequest {

    @NotBlank(message = "La matière première est obligatoire")
    private String matierePremiereGuid;

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "La quantité doit être strictement positive")
    private BigDecimal quantite;

    private String motif;
    private String reference;
}
