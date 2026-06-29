package yamrestor.restor.mapper.comptabilite;

import yamrestor.restor.dto.comptabilite.DepenseDTO;
import yamrestor.restor.entity.comptabilite.DepenseEntity;

public class DepenseMapper {

    public static DepenseDTO toDTO(DepenseEntity d) {
        if (d == null) return null;
        return DepenseDTO.builder()
                .guid(d.getGuid())
                .libelle(d.getLibelle())
                .categorieGuid(d.getCategorie() != null ? d.getCategorie().getGuid() : null)
                .categorieNom(d.getCategorie() != null ? d.getCategorie().getNom() : null)
                .montant(d.getMontant())
                .dateDepense(d.getDateDepense())
                .mode(d.getMode())
                .description(d.getDescription())
                .build();
    }

    private DepenseMapper() {}
}
