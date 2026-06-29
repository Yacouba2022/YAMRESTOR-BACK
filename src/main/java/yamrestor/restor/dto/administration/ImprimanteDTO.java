package yamrestor.restor.dto.administration;

import yamrestor.restor.enums.TypeImprimante;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ImprimanteDTO {
    private String guid;
    private String nom;
    private TypeImprimante type;
    private String adresse;
    private String modele;
    private Boolean actif;
}
