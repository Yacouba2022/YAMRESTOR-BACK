package yamrestor.restor.dto.salle;

import yamrestor.restor.enums.StatutTable;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TableDTO {
    private String guid;
    private String numero;
    private Integer capacite;
    private String salleGuid;
    private String salleNom;
    private StatutTable statut;
    private Boolean actif;
}
