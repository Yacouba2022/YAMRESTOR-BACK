package yamrestor.restor.dto.traiteur;

import yamrestor.restor.enums.TypeVehicule;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VehiculeDTO {
    private String guid;
    private String nom;
    private String immatriculation;
    private TypeVehicule type;
    private Integer kilometrage;
    private Boolean actif;
}
