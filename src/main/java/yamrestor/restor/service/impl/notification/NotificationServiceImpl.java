package yamrestor.restor.service.impl.notification;

import yamrestor.restor.dto.notification.NotificationDTO;
import yamrestor.restor.repository.catalogue.MatierePremiereRepository;
import yamrestor.restor.repository.crm.ClientRepository;
import yamrestor.restor.repository.salle.ReservationRepository;
import yamrestor.restor.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final MatierePremiereRepository matiereRepository;
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> actuelles() {
        LocalDate today = LocalDate.now();
        List<NotificationDTO> notifs = new ArrayList<>();

        // Stock faible
        matiereRepository.findEnAlerte().forEach(m -> notifs.add(NotificationDTO.builder()
                .type("STOCK_FAIBLE").niveau("warning")
                .titre("Stock faible")
                .message(m.getNom() + " : stock " + m.getStock() + " (seuil " + m.getSeuilAlerte() + ")")
                .build()));

        // Péremptions échues ou proches (7 jours)
        matiereRepository.findPerimantAvant(today.plusDays(7)).forEach(m -> {
            boolean echu = m.getDatePeremption() != null && m.getDatePeremption().isBefore(today);
            notifs.add(NotificationDTO.builder()
                    .type("PEREMPTION").niveau(echu ? "danger" : "warning")
                    .titre(echu ? "Produit périmé" : "Péremption proche")
                    .message(m.getNom() + " — " + m.getDatePeremption())
                    .build());
        });

        // Anniversaires du jour
        clientRepository.anniversaires(today.getMonthValue(), today.getDayOfMonth()).forEach(c -> notifs.add(
                NotificationDTO.builder()
                        .type("ANNIVERSAIRE").niveau("info")
                        .titre("Anniversaire client")
                        .message(c.getNom() + " fête son anniversaire aujourd'hui")
                        .build()));

        // Réservations du jour
        reservationRepository.duJour(today).forEach(r -> notifs.add(NotificationDTO.builder()
                .type("RESERVATION").niveau("info")
                .titre("Réservation du jour")
                .message(r.getClientNom() + (r.getHeure() != null ? " à " + r.getHeure() : "")
                        + " (" + (r.getNombrePersonnes() != null ? r.getNombrePersonnes() : "?") + " pers.)")
                .build()));

        return notifs;
    }
}
