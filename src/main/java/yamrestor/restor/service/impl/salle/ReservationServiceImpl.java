package yamrestor.restor.service.impl.salle;

import yamrestor.restor.dto.request.salle.ReservationRequest;
import yamrestor.restor.entity.salle.ReservationEntity;
import yamrestor.restor.entity.salle.TableEntity;
import yamrestor.restor.enums.StatutReservation;
import yamrestor.restor.enums.StatutTable;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.salle.ReservationRepository;
import yamrestor.restor.repository.salle.TableRepository;
import yamrestor.restor.service.salle.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    @Override
    public Page<ReservationEntity> search(LocalDate date, StatutReservation statut, int page, int size) {
        return reservationRepository.search(date, statut, PageRequest.of(page, size));
    }

    @Override
    public ReservationEntity findByGuid(String guid) {
        return reservationRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("ReservationEntity", guid));
    }

    @Override
    @Transactional
    public ReservationEntity creerDepuisRequest(ReservationRequest req) {
        ReservationEntity r = new ReservationEntity();
        apply(r, req);
        return reservationRepository.save(r);
    }

    @Override
    @Transactional
    public ReservationEntity modifierDepuisRequest(String guid, ReservationRequest req) {
        ReservationEntity r = findByGuid(guid);
        apply(r, req);
        return reservationRepository.save(r);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        reservationRepository.delete(findByGuid(guid));
    }

    @Override
    @Transactional
    public ReservationEntity confirmer(String guid) {
        ReservationEntity r = findByGuid(guid);
        r.setStatut(StatutReservation.CONFIRMEE);
        if (r.getTable() != null && r.getTable().getStatut() == StatutTable.LIBRE) {
            r.getTable().setStatut(StatutTable.RESERVEE);
            tableRepository.save(r.getTable());
        }
        return reservationRepository.save(r);
    }

    @Override
    @Transactional
    public ReservationEntity annuler(String guid) {
        ReservationEntity r = findByGuid(guid);
        r.setStatut(StatutReservation.ANNULEE);
        if (r.getTable() != null && r.getTable().getStatut() == StatutTable.RESERVEE) {
            r.getTable().setStatut(StatutTable.LIBRE);
            tableRepository.save(r.getTable());
        }
        return reservationRepository.save(r);
    }

    private void apply(ReservationEntity r, ReservationRequest req) {
        r.setClientNom(req.getClientNom());
        r.setClientTelephone(req.getClientTelephone());
        r.setDateReservation(req.getDateReservation());
        r.setHeure(req.getHeure());
        r.setNombrePersonnes(req.getNombrePersonnes());
        r.setTable(resolveTable(req.getTableGuid()));
        r.setObservations(req.getObservations());
    }

    private TableEntity resolveTable(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return tableRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Table", guid));
    }
}
