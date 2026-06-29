package yamrestor.restor.dto.request.traiteur;

import yamrestor.restor.enums.ModePaiement;
import yamrestor.restor.enums.TypePaiementPrestation;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class PaiementPrestationRequest {

    @NotBlank(message = "La prestation est obligatoire")
    private String prestationGuid;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être strictement positif")
    private BigDecimal montant;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement mode;

    @NotNull(message = "Le type (acompte / solde) est obligatoire")
    private TypePaiementPrestation type;

    private String reference;
}
