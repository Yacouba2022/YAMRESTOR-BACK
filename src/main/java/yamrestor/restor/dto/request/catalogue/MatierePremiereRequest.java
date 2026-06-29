package yamrestor.restor.dto.request.catalogue;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class MatierePremiereRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String code;
    private String uniteGuid;

    @DecimalMin(value = "0.0", message = "Le prix d'achat doit être positif ou nul")
    private BigDecimal prixAchat;

    @DecimalMin(value = "0.0", message = "Le stock doit être positif ou nul")
    private BigDecimal stock;

    @DecimalMin(value = "0.0", message = "Le stock minimum doit être positif ou nul")
    private BigDecimal stockMinimum;

    @DecimalMin(value = "0.0", message = "Le seuil d'alerte doit être positif ou nul")
    private BigDecimal seuilAlerte;

    private LocalDate datePeremption;
    private String emplacement;
    private Boolean actif = true;
}
