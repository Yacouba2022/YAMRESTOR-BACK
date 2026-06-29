package yamrestor.restor.mapper.catalogue;

import yamrestor.restor.dto.catalogue.ProduitDTO;
import yamrestor.restor.entity.catalogue.ProduitEntity;

public class ProduitMapper {

    public static ProduitDTO toDTO(ProduitEntity p) {
        if (p == null) return null;
        return ProduitDTO.builder()
                .guid(p.getGuid())
                .nom(p.getNom())
                .code(p.getCode())
                .description(p.getDescription())
                .categorieGuid(p.getCategorie() != null ? p.getCategorie().getGuid() : null)
                .categorieNom(p.getCategorie() != null ? p.getCategorie().getNom() : null)
                .type(p.getType())
                .prixVente(p.getPrixVente())
                .tauxTva(p.getTauxTva())
                .image(p.getImage())
                .disponible(p.getDisponible())
                .tempsPreparation(p.getTempsPreparation())
                .saisonnier(p.getSaisonnier())
                .actif(p.getActif())
                .build();
    }

    private ProduitMapper() {}
}
