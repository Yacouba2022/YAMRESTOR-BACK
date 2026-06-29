package yamrestor.restor.dto.traiteur;

import yamrestor.restor.enums.TypePersonnelTraiteur;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PersonnelTraiteurDTO {
    private String guid;
    private String nom;
    private TypePersonnelTraiteur fonction;
    private String telephone;
    private BigDecimal coutJournalier;
    private Boolean actif;
}
