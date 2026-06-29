package yamrestor.restor.mapper.salle;

import yamrestor.restor.dto.salle.ReservationDTO;
import yamrestor.restor.entity.salle.ReservationEntity;

public class ReservationMapper {

    public static ReservationDTO toDTO(ReservationEntity r) {
        if (r == null) return null;
        return ReservationDTO.builder()
                .guid(r.getGuid())
                .clientNom(r.getClientNom())
                .clientTelephone(r.getClientTelephone())
                .dateReservation(r.getDateReservation())
                .heure(r.getHeure())
                .nombrePersonnes(r.getNombrePersonnes())
                .tableGuid(r.getTable() != null ? r.getTable().getGuid() : null)
                .tableNumero(r.getTable() != null ? r.getTable().getNumero() : null)
                .observations(r.getObservations())
                .statut(r.getStatut())
                .build();
    }

    private ReservationMapper() {}
}
