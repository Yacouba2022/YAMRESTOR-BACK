package yamrestor.restor.dto.location;

import yamrestor.restor.enums.EtatMateriel;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MaterielLocationDTO {
    private String guid;
    private String nom;
    private String categorie;
    private Integer quantiteTotale;
    private Integer quantiteDisponible;
    private BigDecimal prixLocation;
    private BigDecimal caution;
    private EtatMateriel etat;
    private Boolean actif;
}
