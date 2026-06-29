package yamrestor.restor.dto.caisse;

import yamrestor.restor.enums.StatutSessionCaisse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SessionCaisseDTO {
    private String guid;
    private String numero;
    private String caissierGuid;
    private String caissierNom;
    private BigDecimal fondInitial;
    private LocalDateTime dateOuverture;
    private LocalDateTime dateFermeture;
    private BigDecimal totalEncaisse;
    private BigDecimal totalEspeces;
    private BigDecimal montantTheorique;
    private BigDecimal fondFinalReel;
    private BigDecimal ecart;
    private String commentaire;
    private StatutSessionCaisse statut;
}
