package yamrestor.restor.mapper.traiteur;

import yamrestor.restor.dto.traiteur.VehiculeDTO;
import yamrestor.restor.entity.traiteur.VehiculeEntity;

public class VehiculeMapper {

    public static VehiculeDTO toDTO(VehiculeEntity v) {
        if (v == null) return null;
        return VehiculeDTO.builder()
                .guid(v.getGuid())
                .nom(v.getNom())
                .immatriculation(v.getImmatriculation())
                .type(v.getType())
                .kilometrage(v.getKilometrage())
                .actif(v.getActif())
                .build();
    }

    private VehiculeMapper() {}
}
