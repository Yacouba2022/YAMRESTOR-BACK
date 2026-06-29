package yamrestor.restor.dto.evenement;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/** Élément du calendrier unifié des évènements (réservation, prestation, location). */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CalendrierItemDTO {
    /** RESERVATION | PRESTATION | LOCATION */
    private String type;
    private String reference;
    private String titre;
    private LocalDate date;
    private LocalTime heure;
    private String statut;
}
