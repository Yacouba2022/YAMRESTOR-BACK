package yamrestor.restor.dto.catalogue;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategorieDTO {
    private String guid;
    private String nom;
    private String description;
    private String code;
    private Boolean actif;
}
