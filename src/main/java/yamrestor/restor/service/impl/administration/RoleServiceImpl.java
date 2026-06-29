package yamrestor.restor.service.impl.administration;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.administration.RoleDTO;
import yamrestor.restor.dto.request.administration.RoleRequest;
import yamrestor.restor.entity.administration.PermissionEntity;
import yamrestor.restor.entity.administration.RoleEntity;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.mapper.administration.RoleMapper;
import yamrestor.restor.repository.administration.PermissionRepository;
import yamrestor.restor.repository.administration.RoleRepository;
import yamrestor.restor.service.IntegrityService;
import yamrestor.restor.service.administration.RoleService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final IntegrityService integrityService;

    @Override
    public Page<RoleDTO> findAll(int page, int size) {
        return roleRepository.findAll(PageRequest.of(page, size, Sort.by("nom")))
                .map(RoleMapper::toDTO);
    }

    @Override
    public RoleDTO findByGuid(String guid) {
        return RoleMapper.toDTO(roleRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Role", guid)));
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return roleRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(r -> SelectOptionDTO.of(r.getGuid(), r.getNom())).toList();
    }

    @Override
    @Transactional
    public RoleDTO creerDepuisRequest(RoleRequest req) {
        if (roleRepository.existsByCode(req.getCode())) {
            throw new BadRequestException("Code de rôle déjà utilisé : " + req.getCode());
        }
        RoleEntity role = new RoleEntity();
        role.setNom(req.getNom());
        role.setCode(req.getCode());
        role.setDescription(req.getDescription());
        role.setIsActive(req.getIsActive());
        role.setPermissions(resolvePermissions(req.getPermissionGuids()));
        return RoleMapper.toDTO(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleDTO modifierDepuisRequest(String guid, RoleRequest req) {
        RoleEntity role = roleRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Role", guid));
        roleRepository.findByCode(req.getCode())
                .filter(other -> !other.getGuid().equals(guid))
                .ifPresent(other -> { throw new BadRequestException("Code de rôle déjà utilisé : " + req.getCode()); });
        role.setNom(req.getNom());
        role.setCode(req.getCode());
        role.setDescription(req.getDescription());
        role.setIsActive(req.getIsActive());
        if (req.getPermissionGuids() != null) role.setPermissions(resolvePermissions(req.getPermissionGuids()));
        return RoleMapper.toDTO(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void supprimer(String guid) {
        RoleEntity role = roleRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Role", guid));
        integrityService.checkRoleDeletable(role);
        roleRepository.delete(role);
    }

    /** Convertit une liste de GUIDs de permissions en entités (échoue si l'une est introuvable). */
    private Set<PermissionEntity> resolvePermissions(Set<String> guids) {
        Set<PermissionEntity> permissions = new HashSet<>();
        if (guids == null) return permissions;
        for (String guid : guids) {
            permissions.add(permissionRepository.findByGuid(guid)
                    .orElseThrow(() -> new ResourceNotFoundException("Permission", guid)));
        }
        return permissions;
    }
}
