package yamrestor.restor.dto.request.catalogue;

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
public class RecetteRequest {

    @NotBlank(message = "Le produit est obligatoire")
    private String produitGuid;

    private Integer tempsPreparation;
    private String instructions;

    @Valid
    private List<LigneRequest> lignes = new ArrayList<>();

    @Getter @Setter
    public static class LigneRequest {
        @NotBlank(message = "La matière première est obligatoire")
        private String matierePremiereGuid;

        @NotNull(message = "La quantité est obligatoire")
        @Positive(message = "La quantité doit être strictement positive")
        private BigDecimal quantite;

        private String uniteGuid;
    }
}
