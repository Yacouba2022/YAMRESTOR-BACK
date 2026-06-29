package yamrestor.restor.mapper.livraison;

import yamrestor.restor.dto.livraison.ZoneLivraisonDTO;
import yamrestor.restor.entity.livraison.ZoneLivraisonEntity;

public class ZoneLivraisonMapper {

    public static ZoneLivraisonDTO toDTO(ZoneLivraisonEntity z) {
        if (z == null) return null;
        return ZoneLivraisonDTO.builder()
                .guid(z.getGuid())
                .nom(z.getNom())
                .frais(z.getFrais())
                .actif(z.getActif())
                .build();
    }

    private ZoneLivraisonMapper() {}
}
