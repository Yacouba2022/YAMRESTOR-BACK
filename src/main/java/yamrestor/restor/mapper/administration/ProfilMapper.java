package yamrestor.restor.mapper.administration;

import yamrestor.restor.dto.administration.ProfilDTO;
import yamrestor.restor.entity.administration.ProfilEntity;

import java.util.stream.Collectors;

public class ProfilMapper {

    public static ProfilDTO toDTO(ProfilEntity p) {
        return toDTO(p, null);
    }

    public static ProfilDTO toDTO(ProfilEntity p, Long usersCount) {
        if (p == null) return null;
        return ProfilDTO.builder()
                .guid(p.getGuid())
                .nom(p.getNom())
                .description(p.getDescription())
                .isActive(p.getIsActive())
                .roles(p.getRoles() == null ? null
                        : p.getRoles().stream().map(RoleMapper::toDTO).collect(Collectors.toSet()))
                .permissionsSupplementaires(p.getPermissionsSupplementaires() == null ? null
                        : p.getPermissionsSupplementaires().stream().map(PermissionMapper::toDTO).collect(Collectors.toSet()))
                .permissions(p.getPermissions())
                .usersCount(usersCount)
                .build();
    }

    private ProfilMapper() {}
}
