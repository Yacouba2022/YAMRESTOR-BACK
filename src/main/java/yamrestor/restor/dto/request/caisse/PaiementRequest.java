package yamrestor.restor.dto.request.caisse;

import yamrestor.restor.enums.ModePaiement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PaiementRequest {

    @NotBlank(message = "La commande est obligatoire")
    private String commandeGuid;

    /** Session de caisse (guid). Si absent, l'utilisateur connecté est utilisé pour la résoudre. */
    private String sessionCaisseGuid;

    @DecimalMin(value = "0.0", message = "Le rendu doit être positif ou nul")
    private BigDecimal rendu;

    @Valid
    @NotEmpty(message = "Au moins un mode de paiement est requis")
    private List<LigneRequest> lignes = new ArrayList<>();

    @Getter @Setter
    public static class LigneRequest {
        @NotNull(message = "Le mode de paiement est obligatoire")
        private ModePaiement mode;

        @NotNull(message = "Le montant est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être strictement positif")
        private BigDecimal montant;

        private String reference;
    }
}
