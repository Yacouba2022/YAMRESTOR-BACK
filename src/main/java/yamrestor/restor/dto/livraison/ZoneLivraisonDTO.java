package yamrestor.restor.dto.livraison;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ZoneLivraisonDTO {
    private String guid;
    private String nom;
    private BigDecimal frais;
    private Boolean actif;
}
