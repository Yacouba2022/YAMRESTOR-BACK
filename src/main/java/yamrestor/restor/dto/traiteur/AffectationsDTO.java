package yamrestor.restor.dto.traiteur;

import yamrestor.restor.enums.TypePersonnelTraiteur;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/** Ensemble des affectations (personnel, véhicules, matériel) d'une prestation. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AffectationsDTO {
    private List<PersonnelDTO> personnels;
    private List<VehiculeAffectDTO> vehicules;
    private List<MaterielAffectDTO> materiels;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PersonnelDTO {
        private String guid;
        private String personnelGuid;
        private String personnelNom;
        private TypePersonnelTraiteur fonction;
        private String role;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class VehiculeAffectDTO {
        private String guid;
        private String vehiculeGuid;
        private String vehiculeNom;
        private String chauffeurNom;
        private Integer kmDepart;
        private Integer kmRetour;
        private BigDecimal carburant;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MaterielAffectDTO {
        private String guid;
        private String materielGuid;
        private String materielNom;
        private Integer quantite;
    }
}
