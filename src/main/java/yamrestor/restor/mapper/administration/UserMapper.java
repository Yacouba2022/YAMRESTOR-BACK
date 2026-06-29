package yamrestor.restor.mapper.administration;

import yamrestor.restor.dto.administration.UserDTO;
import yamrestor.restor.entity.administration.UserEntity;

public class UserMapper {

    public static UserDTO toDTO(UserEntity u) {
        if (u == null) return null;
        return UserDTO.builder()
                .guid(u.getGuid())
                .name(u.getName())
                .email(u.getEmail())
                .fonction(u.getFonction())
                .telephone(u.getTelephone())
                .adresse(u.getAdresse())
                .compte(u.getCompte())
                .etat(u.getEtat())
                .profilePhotoPath(u.getProfilePhotoPath())
                .preferences(u.getPreferences())
                .profilGuid(u.getProfil() != null ? u.getProfil().getGuid() : null)
                .profilNom(u.getProfil() != null ? u.getProfil().getNom() : null)
                .build();
    }

    private UserMapper() {}
}
