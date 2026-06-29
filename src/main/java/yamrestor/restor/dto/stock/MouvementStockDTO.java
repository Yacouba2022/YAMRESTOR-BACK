package yamrestor.restor.dto.stock;

import yamrestor.restor.enums.TypeMouvement;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MouvementStockDTO {
    private String guid;
    private String matierePremiereGuid;
    private String matierePremiereNom;
    private TypeMouvement type;
    private BigDecimal quantite;
    private BigDecimal stockAvant;
    private BigDecimal stockApres;
    private String motif;
    private String reference;
    private LocalDateTime createdAt;
}
