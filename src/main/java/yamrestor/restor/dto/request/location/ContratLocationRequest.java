package yamrestor.restor.dto.request.location;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ContratLocationRequest {

    @NotBlank(message = "Le nom du client est obligatoire")
    private String clientNom;

    private String clientTelephone;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    private LocalDate dateFin;
    private String observations;

    @Valid
    private List<LigneRequest> lignes = new ArrayList<>();

    @Getter @Setter
    public static class LigneRequest {
        @NotBlank(message = "Le matériel est obligatoire")
        private String materielGuid;

        @NotNull(message = "La quantité est obligatoire")
        @Min(value = 1, message = "La quantité doit être au moins 1")
        private Integer quantite;

        /** Prix unitaire ; si absent, repris du prix de location du matériel. */
        private BigDecimal prixUnitaire;
    }
}
