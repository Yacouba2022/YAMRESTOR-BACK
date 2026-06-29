package yamrestor.restor.mapper.stock;

import yamrestor.restor.dto.stock.MouvementStockDTO;
import yamrestor.restor.entity.stock.MouvementStockEntity;

public class MouvementStockMapper {

    public static MouvementStockDTO toDTO(MouvementStockEntity m) {
        if (m == null) return null;
        return MouvementStockDTO.builder()
                .guid(m.getGuid())
                .matierePremiereGuid(m.getMatierePremiere() != null ? m.getMatierePremiere().getGuid() : null)
                .matierePremiereNom(m.getMatierePremiere() != null ? m.getMatierePremiere().getNom() : null)
                .type(m.getType())
                .quantite(m.getQuantite())
                .stockAvant(m.getStockAvant())
                .stockApres(m.getStockApres())
                .motif(m.getMotif())
                .reference(m.getReference())
                .createdAt(m.getCreatedAt())
                .build();
    }

    private MouvementStockMapper() {}
}
