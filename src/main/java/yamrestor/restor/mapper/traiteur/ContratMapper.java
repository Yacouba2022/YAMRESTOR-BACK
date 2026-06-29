package yamrestor.restor.mapper.traiteur;

import yamrestor.restor.dto.traiteur.ContratDTO;
import yamrestor.restor.entity.traiteur.ContratEntity;

public class ContratMapper {

    public static ContratDTO toDTO(ContratEntity c) {
        if (c == null) return null;
        return ContratDTO.builder()
                .guid(c.getGuid())
                .numero(c.getNumero())
                .prestationGuid(c.getPrestation() != null ? c.getPrestation().getGuid() : null)
                .prestationNumero(c.getPrestation() != null ? c.getPrestation().getNumero() : null)
                .devisGuid(c.getDevis() != null ? c.getDevis().getGuid() : null)
                .montant(c.getMontant())
                .conditions(c.getConditions())
                .dateSignature(c.getDateSignature())
                .statut(c.getStatut())
                .build();
    }

    private ContratMapper() {}
}
