package yamrestor.restor.mapper.salle;

import yamrestor.restor.dto.salle.SalleDTO;
import yamrestor.restor.entity.salle.SalleEntity;

public class SalleMapper {

    public static SalleDTO toDTO(SalleEntity s) {
        if (s == null) return null;
        return SalleDTO.builder()
                .guid(s.getGuid())
                .nom(s.getNom())
                .description(s.getDescription())
                .actif(s.getActif())
                .build();
    }

    private SalleMapper() {}
}
