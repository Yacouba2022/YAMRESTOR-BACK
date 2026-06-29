package yamrestor.restor.mapper.crm;

import yamrestor.restor.dto.crm.BonAchatDTO;
import yamrestor.restor.entity.crm.BonAchatEntity;

public class BonAchatMapper {

    public static BonAchatDTO toDTO(BonAchatEntity b) {
        if (b == null) return null;
        return BonAchatDTO.builder()
                .guid(b.getGuid())
                .code(b.getCode())
                .clientGuid(b.getClient() != null ? b.getClient().getGuid() : null)
                .clientNom(b.getClient() != null ? b.getClient().getNom() : null)
                .montant(b.getMontant())
                .pointsUtilises(b.getPointsUtilises())
                .statut(b.getStatut())
                .dateExpiration(b.getDateExpiration())
                .build();
    }

    private BonAchatMapper() {}
}
