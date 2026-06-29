package yamrestor.restor.service.impl.evenement;

import yamrestor.restor.dto.evenement.CalendrierItemDTO;
import yamrestor.restor.repository.location.ContratLocationRepository;
import yamrestor.restor.repository.salle.ReservationRepository;
import yamrestor.restor.repository.traiteur.PrestationRepository;
import yamrestor.restor.service.evenement.EvenementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvenementServiceImpl implements EvenementService {

    private final ReservationRepository reservationRepository;
    private final PrestationRepository prestationRepository;
    private final ContratLocationRepository contratLocationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CalendrierItemDTO> calendrier(LocalDate debut, LocalDate fin) {
        LocalDate d = debut != null ? debut : LocalDate.now().withDayOfMonth(1);
        LocalDate f = fin != null ? fin : d.plusMonths(1);

        List<CalendrierItemDTO> items = new ArrayList<>();

        reservationRepository.entrePeriode(d, f).forEach(r -> items.add(CalendrierItemDTO.builder()
                .type("RESERVATION")
                .reference(r.getGuid())
                .titre("Réservation — " + r.getClientNom()
                        + (r.getTable() != null ? " (table " + r.getTable().getNumero() + ")" : ""))
                .date(r.getDateReservation())
                .heure(r.getHeure())
                .statut(r.getStatut().name())
                .build()));

        prestationRepository.entrePeriode(d, f).forEach(p -> items.add(CalendrierItemDTO.builder()
                .type("PRESTATION")
                .reference(p.getNumero())
                .titre(p.getTypeEvenement().name() + " — " + (p.getClientNom() != null ? p.getClientNom() : "")
                        + (p.getLieu() != null ? " @ " + p.getLieu() : ""))
                .date(p.getDateEvenement())
                .heure(p.getHeure())
                .statut(p.getStatut().name())
                .build()));

        contratLocationRepository.entrePeriode(d, f).forEach(c -> items.add(CalendrierItemDTO.builder()
                .type("LOCATION")
                .reference(c.getNumero())
                .titre("Location — " + c.getClientNom())
                .date(c.getDateDebut())
                .statut(c.getStatut().name())
                .build()));

        items.sort(Comparator.comparing(CalendrierItemDTO::getDate,
                Comparator.nullsLast(Comparator.naturalOrder())));
        return items;
    }
}
