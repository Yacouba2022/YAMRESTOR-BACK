package yamrestor.restor.dto.traiteur;

import lombok.*;

import java.math.BigDecimal;

/** Rentabilité d'une prestation : chiffre d'affaires − coûts = bénéfice. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RentabilitePrestationDTO {
    private String prestationGuid;
    private String numero;
    private BigDecimal chiffreAffaires;
    private BigDecimal coutMatieresPremieres;
    private BigDecimal coutPersonnel;
    private BigDecimal coutTransport;
    private BigDecimal coutMateriel;
    private BigDecimal coutDivers;
    private BigDecimal coutTotal;
    private BigDecimal benefice;
    private BigDecimal montantPaye;
    private BigDecimal resteAPayer;
}
