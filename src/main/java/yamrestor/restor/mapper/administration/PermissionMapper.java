package yamrestor.restor.mapper.administration;

import yamrestor.restor.dto.administration.PermissionDTO;
import yamrestor.restor.entity.administration.PermissionEntity;

public class PermissionMapper {

    public static PermissionDTO toDTO(PermissionEntity p) {
        if (p == null) return null;
        return PermissionDTO.builder()
                .guid(p.getGuid())
                .nom(p.getNom())
                .code(p.getCode())
                .description(p.getDescription())
                .module(p.getModule())
                .build();
    }

    private PermissionMapper() {}
}
