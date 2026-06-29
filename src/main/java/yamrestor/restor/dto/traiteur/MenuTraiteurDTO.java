package yamrestor.restor.dto.traiteur;

import yamrestor.restor.enums.TypeMenuTraiteur;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuTraiteurDTO {
    private String guid;
    private String nom;
    private TypeMenuTraiteur type;
    private BigDecimal prixParPersonne;
    private BigDecimal prixForfaitaire;
    private String description;
    private Boolean actif;
}
