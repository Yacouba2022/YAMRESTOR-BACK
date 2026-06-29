package yamrestor.restor.dto.request.caisse;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class FermerSessionRequest {

    @NotNull(message = "Le montant compté est obligatoire")
    @DecimalMin(value = "0.0", message = "Le montant compté doit être positif ou nul")
    private BigDecimal fondFinalReel;

    private String commentaire;
}
