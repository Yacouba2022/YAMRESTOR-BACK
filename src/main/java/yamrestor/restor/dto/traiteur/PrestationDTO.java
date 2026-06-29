package yamrestor.restor.dto.traiteur;

import yamrestor.restor.enums.StatutPrestation;
import yamrestor.restor.enums.TypeEvenement;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PrestationDTO {
    private String guid;
    private String numero;
    private TypeEvenement typeEvenement;
    private LocalDate dateEvenement;
    private LocalTime heure;
    private String lieu;
    private String clientNom;
    private String clientTelephone;
    private String responsableGuid;
    private String responsableNom;
    private Integer nombreConvivesPrevu;
    private Integer nombreAdultes;
    private Integer nombreEnfants;
    private String observations;
    private StatutPrestation statut;
    private BigDecimal montantTotal;
    private BigDecimal montantPaye;
    private BigDecimal resteAPayer;
    private BigDecimal coutMatieresPremieres;
    private BigDecimal coutPersonnel;
    private BigDecimal coutTransport;
    private BigDecimal coutMateriel;
    private BigDecimal coutDivers;
}
