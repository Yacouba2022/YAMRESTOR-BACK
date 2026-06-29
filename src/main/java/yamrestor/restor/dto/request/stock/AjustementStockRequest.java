package yamrestor.restor.dto.request.stock;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** Ajustement d'inventaire : fixe le stock à la valeur comptée. */
@Getter @Setter
public class AjustementStockRequest {

    @NotBlank(message = "La matière première est obligatoire")
    private String matierePremiereGuid;

    @NotNull(message = "Le nouveau stock est obligatoire")
    @DecimalMin(value = "0.0", message = "Le stock doit être positif ou nul")
    private BigDecimal nouveauStock;

    private String motif;
}
