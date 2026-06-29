package yamrestor.restor.dto.request.caisse;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class OuvrirSessionRequest {

    @NotNull(message = "Le fond initial est obligatoire")
    @DecimalMin(value = "0.0", message = "Le fond initial doit être positif ou nul")
    private BigDecimal fondInitial;

    /** Caissier (guid). Si absent, l'utilisateur connecté est utilisé. */
    private String caissierGuid;
}
