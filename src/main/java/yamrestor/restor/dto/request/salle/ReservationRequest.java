package yamrestor.restor.dto.request.salle;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class ReservationRequest {

    @NotBlank(message = "Le nom du client est obligatoire")
    private String clientNom;

    private String clientTelephone;

    @NotNull(message = "La date est obligatoire")
    private LocalDate dateReservation;

    private LocalTime heure;

    @Min(value = 1, message = "Le nombre de personnes doit être au moins 1")
    private Integer nombrePersonnes;

    private String tableGuid;
    private String observations;
}
