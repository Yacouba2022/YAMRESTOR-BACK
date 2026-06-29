package yamrestor.restor.service.salle;

import yamrestor.restor.dto.request.salle.ReservationRequest;
import yamrestor.restor.entity.salle.ReservationEntity;
import yamrestor.restor.enums.StatutReservation;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface ReservationService {
    Page<ReservationEntity> search(LocalDate date, StatutReservation statut, int page, int size);
    ReservationEntity findByGuid(String guid);
    ReservationEntity creerDepuisRequest(ReservationRequest req);
    ReservationEntity modifierDepuisRequest(String guid, ReservationRequest req);
    void delete(String guid);
    /** Confirme la réservation (et marque la table RESERVEE si une table est associée). */
    ReservationEntity confirmer(String guid);
    /** Annule la réservation (et libère la table si elle était réservée). */
    ReservationEntity annuler(String guid);
}
