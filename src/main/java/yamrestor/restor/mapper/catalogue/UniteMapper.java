package yamrestor.restor.mapper.catalogue;

import yamrestor.restor.dto.catalogue.UniteDTO;
import yamrestor.restor.entity.catalogue.UniteEntity;

public class UniteMapper {

    public static UniteDTO toDTO(UniteEntity u) {
        if (u == null) return null;
        return UniteDTO.builder()
                .guid(u.getGuid())
                .nom(u.getNom())
                .code(u.getCode())
                .symbole(u.getSymbole())
                .actif(u.getActif())
                .build();
    }

    private UniteMapper() {}
}
