package yamrestor.restor.dto.crm;

import yamrestor.restor.enums.StatutBonAchat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BonAchatDTO {
    private String guid;
    private String code;
    private String clientGuid;
    private String clientNom;
    private BigDecimal montant;
    private Integer pointsUtilises;
    private StatutBonAchat statut;
    private LocalDate dateExpiration;
}
