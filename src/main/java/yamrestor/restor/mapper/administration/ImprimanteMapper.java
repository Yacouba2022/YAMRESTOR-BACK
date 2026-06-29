package yamrestor.restor.mapper.administration;

import yamrestor.restor.dto.administration.ImprimanteDTO;
import yamrestor.restor.entity.administration.ImprimanteEntity;

public class ImprimanteMapper {

    public static ImprimanteDTO toDTO(ImprimanteEntity i) {
        if (i == null) return null;
        return ImprimanteDTO.builder()
                .guid(i.getGuid())
                .nom(i.getNom())
                .type(i.getType())
                .adresse(i.getAdresse())
                .modele(i.getModele())
                .actif(i.getActif())
                .build();
    }

    private ImprimanteMapper() {}
}
