package yamrestor.restor.dto.request.approvisionnement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommandeAchatRequest {

    @NotBlank(message = "Le fournisseur est obligatoire")
    private String fournisseurGuid;

    private String observations;

    @Valid
    private List<LigneRequest> lignes = new ArrayList<>();

    @Getter @Setter
    public static class LigneRequest {
        @NotBlank(message = "La matière première est obligatoire")
        private String matierePremiereGuid;

        @NotNull(message = "La quantité est obligatoire")
        @Positive(message = "La quantité doit être strictement positive")
        private BigDecimal quantite;

        /** Prix unitaire d'achat ; si absent, repris du prix d'achat de la matière. */
        private BigDecimal prixUnitaire;
    }
}
