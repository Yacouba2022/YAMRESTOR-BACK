package yamrestor.restor.dto.request.traiteur;

import yamrestor.restor.enums.TypeEvenement;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class PrestationRequest {

    @NotNull(message = "Le type d'évènement est obligatoire")
    private TypeEvenement typeEvenement;

    @NotNull(message = "La date de l'évènement est obligatoire")
    private LocalDate dateEvenement;

    private LocalTime heure;
    private String lieu;
    private String clientNom;
    private String clientTelephone;
    private String responsableGuid;
    private Integer nombreConvivesPrevu;
    private Integer nombreAdultes;
    private Integer nombreEnfants;
    private String observations;

    // Coûts (rentabilité)
    private BigDecimal coutMatieresPremieres;
    private BigDecimal coutPersonnel;
    private BigDecimal coutTransport;
    private BigDecimal coutMateriel;
    private BigDecimal coutDivers;
}
