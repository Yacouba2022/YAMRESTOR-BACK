package yamrestor.restor.dto.request.traiteur;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class AffectationVehiculeRequest {
    @NotBlank(message = "Le véhicule est obligatoire")
    private String vehiculeGuid;
    private String chauffeurNom;
    private Integer kmDepart;
    private Integer kmRetour;
    private BigDecimal carburant;
}
