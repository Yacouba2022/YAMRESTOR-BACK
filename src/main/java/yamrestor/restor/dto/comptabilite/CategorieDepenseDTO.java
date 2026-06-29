package yamrestor.restor.dto.comptabilite;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategorieDepenseDTO {
    private String guid;
    private String nom;
    private String code;
    private Boolean actif;
}
