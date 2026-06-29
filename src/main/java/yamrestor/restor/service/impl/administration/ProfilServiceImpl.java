package yamrestor.restor.service.impl.administration;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.administration.ProfilDTO;
import yamrestor.restor.dto.request.administration.ProfilRequest;
import yamrestor.restor.entity.administration.PermissionEntity;
import yamrestor.restor.entity.administration.ProfilEntity;
import yamrestor.restor.entity.administration.RoleEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.mapper.administration.ProfilMapper;
import yamrestor.restor.repository.administration.PermissionRepository;
import yamrestor.restor.repository.administration.ProfilRepository;
import yamrestor.restor.repository.administration.RoleRepository;
import yamrestor.restor.repository.administration.UserRepository;
import yamrestor.restor.service.IntegrityService;
import yamrestor.restor.service.administration.ProfilService;
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
public class ProfilServiceImpl implements ProfilService {

    private final ProfilRepository profilRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final IntegrityService integrityService;

    @Override
    public Page<ProfilDTO> findAll(int page, int size) {
        return profilRepository.findAll(PageRequest.of(page, size, Sort.by("nom")))
                .map(p -> ProfilMapper.toDTO(p, userRepository.countByProfil(p)));
    }

    @Override
    public ProfilDTO findByGuid(String guid) {
        ProfilEntity p = profilRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Profil", guid));
        return ProfilMapper.toDTO(p, userRepository.countByProfil(p));
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return profilRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(p -> SelectOptionDTO.of(p.getGuid(), p.getNom())).toList();
    }

    @Override
    @Transactional
    public ProfilDTO creerDepuisRequest(ProfilRequest req) {
        ProfilEntity profil = new ProfilEntity();
        profil.setNom(req.getNom());
        profil.setDescription(req.getDescription());
        profil.setIsActive(req.getIsActive());
        profil.setRoles(resolveRoles(req.getRoleGuids()));
        profil.setPermissionsSupplementaires(resolvePermissions(req.getPermissionGuids()));
        ProfilEntity saved = profilRepository.save(profil);
        return ProfilMapper.toDTO(saved, userRepository.countByProfil(saved));
    }

    @Override
    @Transactional
    public ProfilDTO modifierDepuisRequest(String guid, ProfilRequest req) {
        ProfilEntity p = profilRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Profil", guid));
        p.setNom(req.getNom());
        p.setDescription(req.getDescription());
        p.setIsActive(req.getIsActive());
        if (req.getRoleGuids() != null) p.setRoles(resolveRoles(req.getRoleGuids()));
        if (req.getPermissionGuids() != null) p.setPermissionsSupplementaires(resolvePermissions(req.getPermissionGuids()));
        ProfilEntity saved = profilRepository.save(p);
        return ProfilMapper.toDTO(saved, userRepository.countByProfil(saved));
    }

    @Override
    @Transactional
    public void supprimer(String guid) {
        ProfilEntity p = profilRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Profil", guid));
        integrityService.checkProfilDeletable(p);
        profilRepository.delete(p);
    }

    @Override
    @Transactional
    public ProfilDTO dupliquer(String guid) {
        ProfilEntity src = profilRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Profil", guid));
        ProfilEntity copie = new ProfilEntity();
        copie.setNom(src.getNom() + " (Copie)");
        copie.setDescription(src.getDescription());
        copie.setIsActive(src.getIsActive());
        copie.setRoles(new HashSet<>(src.getRoles()));
        copie.setPermissionsSupplementaires(new HashSet<>(src.getPermissionsSupplementaires()));
        ProfilEntity saved = profilRepository.save(copie);
        return ProfilMapper.toDTO(saved, userRepository.countByProfil(saved));
    }

    /** Convertit une liste de GUIDs de rôles en entités (échoue si l'un est introuvable). */
    private Set<RoleEntity> resolveRoles(Set<String> guids) {
        Set<RoleEntity> roles = new HashSet<>();
        if (guids == null) return roles;
        for (String guid : guids) {
            roles.add(roleRepository.findByGuid(guid)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", guid)));
        }
        return roles;
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
