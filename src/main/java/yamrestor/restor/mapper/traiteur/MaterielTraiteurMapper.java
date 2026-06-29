package yamrestor.restor.mapper.traiteur;

import yamrestor.restor.dto.traiteur.MaterielTraiteurDTO;
import yamrestor.restor.entity.traiteur.MaterielTraiteurEntity;

public class MaterielTraiteurMapper {

    public static MaterielTraiteurDTO toDTO(MaterielTraiteurEntity m) {
        if (m == null) return null;
        return MaterielTraiteurDTO.builder()
                .guid(m.getGuid())
                .nom(m.getNom())
                .categorie(m.getCategorie())
                .quantiteTotale(m.getQuantiteTotale())
                .quantiteDisponible(m.getQuantiteDisponible())
                .etat(m.getEtat())
                .actif(m.getActif())
                .build();
    }

    private MaterielTraiteurMapper() {}
}
