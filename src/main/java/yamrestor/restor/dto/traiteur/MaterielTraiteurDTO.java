package yamrestor.restor.dto.traiteur;

import yamrestor.restor.enums.EtatMateriel;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MaterielTraiteurDTO {
    private String guid;
    private String nom;
    private String categorie;
    private Integer quantiteTotale;
    private Integer quantiteDisponible;
    private EtatMateriel etat;
    private Boolean actif;
}
