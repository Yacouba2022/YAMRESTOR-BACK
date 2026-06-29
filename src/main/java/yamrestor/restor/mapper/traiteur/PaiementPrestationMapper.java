package yamrestor.restor.mapper.traiteur;

import yamrestor.restor.dto.traiteur.PaiementPrestationDTO;
import yamrestor.restor.entity.traiteur.PaiementPrestationEntity;

public class PaiementPrestationMapper {

    public static PaiementPrestationDTO toDTO(PaiementPrestationEntity p) {
        if (p == null) return null;
        return PaiementPrestationDTO.builder()
                .guid(p.getGuid())
                .numero(p.getNumero())
                .prestationGuid(p.getPrestation() != null ? p.getPrestation().getGuid() : null)
                .prestationNumero(p.getPrestation() != null ? p.getPrestation().getNumero() : null)
                .montant(p.getMontant())
                .mode(p.getMode())
                .type(p.getType())
                .reference(p.getReference())
                .statut(p.getStatut())
                .createdAt(p.getCreatedAt())
                .build();
    }

    private PaiementPrestationMapper() {}
}
