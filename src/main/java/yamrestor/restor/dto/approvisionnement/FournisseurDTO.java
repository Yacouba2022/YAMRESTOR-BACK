package yamrestor.restor.dto.approvisionnement;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FournisseurDTO {
    private String guid;
    private String nom;
    private String telephone;
    private String email;
    private String adresse;
    private BigDecimal solde;
    private Boolean actif;
}
