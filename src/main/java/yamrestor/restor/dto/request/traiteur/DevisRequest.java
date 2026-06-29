package yamrestor.restor.dto.request.traiteur;

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
public class DevisRequest {

    @NotBlank(message = "La prestation est obligatoire")
    private String prestationGuid;

    private String observations;

    @Valid
    private List<LigneRequest> lignes = new ArrayList<>();

    @Getter @Setter
    public static class LigneRequest {
        @NotBlank(message = "La désignation est obligatoire")
        private String designation;

        private String menuTraiteurGuid;

        @NotNull(message = "La quantité est obligatoire")
        @Positive(message = "La quantité doit être strictement positive")
        private BigDecimal quantite;

        @NotNull(message = "Le prix unitaire est obligatoire")
        private BigDecimal prixUnitaire;
    }
}
