package yamrestor.restor.mapper.approvisionnement;

import yamrestor.restor.dto.approvisionnement.FournisseurDTO;
import yamrestor.restor.entity.approvisionnement.FournisseurEntity;

public class FournisseurMapper {

    public static FournisseurDTO toDTO(FournisseurEntity f) {
        if (f == null) return null;
        return FournisseurDTO.builder()
                .guid(f.getGuid())
                .nom(f.getNom())
                .telephone(f.getTelephone())
                .email(f.getEmail())
                .adresse(f.getAdresse())
                .solde(f.getSolde())
                .actif(f.getActif())
                .build();
    }

    private FournisseurMapper() {}
}
