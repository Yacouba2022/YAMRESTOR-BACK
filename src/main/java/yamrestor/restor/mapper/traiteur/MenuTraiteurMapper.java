package yamrestor.restor.mapper.traiteur;

import yamrestor.restor.dto.traiteur.MenuTraiteurDTO;
import yamrestor.restor.entity.traiteur.MenuTraiteurEntity;

public class MenuTraiteurMapper {

    public static MenuTraiteurDTO toDTO(MenuTraiteurEntity m) {
        if (m == null) return null;
        return MenuTraiteurDTO.builder()
                .guid(m.getGuid())
                .nom(m.getNom())
                .type(m.getType())
                .prixParPersonne(m.getPrixParPersonne())
                .prixForfaitaire(m.getPrixForfaitaire())
                .description(m.getDescription())
                .actif(m.getActif())
                .build();
    }

    private MenuTraiteurMapper() {}
}
