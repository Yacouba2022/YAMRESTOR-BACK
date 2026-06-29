package yamrestor.restor.dto.request.approvisionnement;

import yamrestor.restor.enums.ModePaiement;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class PaiementAchatRequest {

    @NotBlank(message = "Le fournisseur est obligatoire")
    private String fournisseurGuid;

    private String commandeAchatGuid;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être strictement positif")
    private BigDecimal montant;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement mode;

    private String reference;
}
