package yamrestor.restor.dto.request.crm;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class ClientRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String telephone;
    private String email;
    private String adresse;
    private LocalDate dateNaissance;
    private String preferences;

    @DecimalMin(value = "0.0", message = "La remise doit être positive ou nulle")
    @DecimalMax(value = "100.0", message = "La remise ne peut pas dépasser 100 %")
    private BigDecimal remisePourcentage;

    private Boolean actif = true;
}
