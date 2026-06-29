package yamrestor.restor.dto.request.salle;

import yamrestor.restor.enums.Priorite;
import yamrestor.restor.enums.TypeCommande;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommandeRequest {

    @NotNull(message = "Le type de commande est obligatoire")
    private TypeCommande type;

    private String tableGuid;
    private String serveurGuid;
    private String clientNom;
    private String clientTelephone;
    private Priorite priorite;
    private String observations;

    @Valid
    private List<LigneRequest> lignes = new ArrayList<>();

    @Getter @Setter
    public static class LigneRequest {
        @NotBlank(message = "Le produit est obligatoire")
        private String produitGuid;

        @NotNull(message = "La quantité est obligatoire")
        @Min(value = 1, message = "La quantité doit être au moins 1")
        private Integer quantite;

        private String notes;
    }
}
