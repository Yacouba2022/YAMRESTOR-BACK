package yamrestor.restor.dto.request.comptabilite;

import yamrestor.restor.enums.ModePaiement;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class DepenseRequest {

    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;

    private String categorieGuid;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être strictement positif")
    private BigDecimal montant;

    @NotNull(message = "La date est obligatoire")
    private LocalDate dateDepense;

    private ModePaiement mode;
    private String description;
}
