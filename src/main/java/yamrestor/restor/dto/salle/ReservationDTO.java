package yamrestor.restor.dto.salle;

import yamrestor.restor.enums.StatutReservation;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationDTO {
    private String guid;
    private String clientNom;
    private String clientTelephone;
    private LocalDate dateReservation;
    private LocalTime heure;
    private Integer nombrePersonnes;
    private String tableGuid;
    private String tableNumero;
    private String observations;
    private StatutReservation statut;
}
