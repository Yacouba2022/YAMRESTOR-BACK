package yamrestor.restor.mapper.salle;

import yamrestor.restor.dto.salle.TableDTO;
import yamrestor.restor.entity.salle.TableEntity;

public class TableMapper {

    public static TableDTO toDTO(TableEntity t) {
        if (t == null) return null;
        return TableDTO.builder()
                .guid(t.getGuid())
                .numero(t.getNumero())
                .capacite(t.getCapacite())
                .salleGuid(t.getSalle() != null ? t.getSalle().getGuid() : null)
                .salleNom(t.getSalle() != null ? t.getSalle().getNom() : null)
                .statut(t.getStatut())
                .actif(t.getActif())
                .build();
    }

    private TableMapper() {}
}
