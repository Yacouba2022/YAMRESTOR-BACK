package yamrestor.restor.dto.catalogue;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UniteDTO {
    private String guid;
    private String nom;
    private String code;
    private String symbole;
    private Boolean actif;
}
