package yamrestor.restor.dto.request.salle;

import yamrestor.restor.enums.StatutTable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TableRequest {

    @NotBlank(message = "Le numéro est obligatoire")
    private String numero;

    @Min(value = 1, message = "La capacité doit être au moins 1")
    private Integer capacite;

    private String salleGuid;
    private StatutTable statut;
    private Boolean actif = true;
}
