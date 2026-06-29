package yamrestor.restor.mapper.catalogue;

import yamrestor.restor.dto.catalogue.CategorieDTO;
import yamrestor.restor.entity.catalogue.CategorieEntity;

public class CategorieMapper {

    public static CategorieDTO toDTO(CategorieEntity c) {
        if (c == null) return null;
        return CategorieDTO.builder()
                .guid(c.getGuid())
                .nom(c.getNom())
                .description(c.getDescription())
                .code(c.getCode())
                .actif(c.getActif())
                .build();
    }

    private CategorieMapper() {}
}
