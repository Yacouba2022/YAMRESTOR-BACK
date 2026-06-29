package yamrestor.restor.mapper.location;

import yamrestor.restor.dto.location.MaterielLocationDTO;
import yamrestor.restor.entity.location.MaterielLocationEntity;

public class MaterielLocationMapper {

    public static MaterielLocationDTO toDTO(MaterielLocationEntity m) {
        if (m == null) return null;
        return MaterielLocationDTO.builder()
                .guid(m.getGuid())
                .nom(m.getNom())
                .categorie(m.getCategorie())
                .quantiteTotale(m.getQuantiteTotale())
                .quantiteDisponible(m.getQuantiteDisponible())
                .prixLocation(m.getPrixLocation())
                .caution(m.getCaution())
                .etat(m.getEtat())
                .actif(m.getActif())
                .build();
    }

    private MaterielLocationMapper() {}
}
