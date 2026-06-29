package yamrestor.restor.service.evenement;

import yamrestor.restor.dto.evenement.CalendrierItemDTO;

import java.time.LocalDate;
import java.util.List;

public interface EvenementService {
    /** Calendrier unifié sur une période : réservations + prestations traiteur + locations. */
    List<CalendrierItemDTO> calendrier(LocalDate debut, LocalDate fin);
}
