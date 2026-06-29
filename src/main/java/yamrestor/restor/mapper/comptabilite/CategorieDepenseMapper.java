package yamrestor.restor.mapper.comptabilite;

import yamrestor.restor.dto.comptabilite.CategorieDepenseDTO;
import yamrestor.restor.entity.comptabilite.CategorieDepenseEntity;

public class CategorieDepenseMapper {

    public static CategorieDepenseDTO toDTO(CategorieDepenseEntity c) {
        if (c == null) return null;
        return CategorieDepenseDTO.builder()
                .guid(c.getGuid())
                .nom(c.getNom())
                .code(c.getCode())
                .actif(c.getActif())
                .build();
    }

    private CategorieDepenseMapper() {}
}
