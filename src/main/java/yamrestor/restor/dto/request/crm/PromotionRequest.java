package yamrestor.restor.dto.request.crm;

import yamrestor.restor.enums.TypePromotion;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class PromotionRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotNull(message = "Le type est obligatoire")
    private TypePromotion type;

    @DecimalMin(value = "0.0", message = "La valeur doit être positive ou nulle")
    private BigDecimal valeur;

    private String code;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Boolean actif = true;
}
