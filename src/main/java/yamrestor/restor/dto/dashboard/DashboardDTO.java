package yamrestor.restor.dto.dashboard;

import yamrestor.restor.dto.rapport.ProduitVenduDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/** Synthèse du tableau de bord (chiffres du jour + indicateurs). */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardDTO {
    private BigDecimal chiffreAffairesJour;
    private long nbCommandesJour;
    private long commandesEnAttente;
    private long reservationsJour;
    private long alertesStock;
    private BigDecimal valorisationStock;
    private List<ProduitVenduDTO> topProduitsJour;
}
