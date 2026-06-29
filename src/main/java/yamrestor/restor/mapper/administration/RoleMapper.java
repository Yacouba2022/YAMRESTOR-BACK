package yamrestor.restor.mapper.administration;

import yamrestor.restor.dto.administration.RoleDTO;
import yamrestor.restor.entity.administration.RoleEntity;

import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleDTO toDTO(RoleEntity r) {
        if (r == null) return null;
        return RoleDTO.builder()
                .guid(r.getGuid())
                .nom(r.getNom())
                .code(r.getCode())
                .description(r.getDescription())
                .isActive(r.getIsActive())
                .permissions(r.getPermissions() == null ? null
                        : r.getPermissions().stream()
                                .map(PermissionMapper::toDTO)
                                .collect(Collectors.toSet()))
                .build();
    }

    private RoleMapper() {}
}
