package yamrestor.restor.dto.request.traiteur;

import yamrestor.restor.enums.TypePersonnelTraiteur;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class PersonnelTraiteurRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotNull(message = "La fonction est obligatoire")
    private TypePersonnelTraiteur fonction;

    private String telephone;

    @DecimalMin(value = "0.0", message = "Le coût journalier doit être positif ou nul")
    private BigDecimal coutJournalier;

    private Boolean actif = true;
}
