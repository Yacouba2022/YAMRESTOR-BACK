package yamrestor.restor.service.impl.administration;

import yamrestor.restor.dto.administration.PermissionDTO;
import yamrestor.restor.dto.request.administration.PermissionRequest;
import yamrestor.restor.entity.administration.PermissionEntity;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.mapper.administration.PermissionMapper;
import yamrestor.restor.repository.administration.PermissionRepository;
import yamrestor.restor.service.IntegrityService;
import yamrestor.restor.service.administration.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final IntegrityService integrityService;

    @Override
    public Page<PermissionDTO> findAll(int page, int size) {
        return permissionRepository
                .findAll(PageRequest.of(page, size, Sort.by("module", "code")))
                .map(PermissionMapper::toDTO);
    }

    @Override
    public PermissionDTO findByGuid(String guid) {
        return PermissionMapper.toDTO(permissionRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", guid)));
    }

    @Override
    @Transactional
    public PermissionDTO creerDepuisRequest(PermissionRequest req) {
        if (permissionRepository.existsByCode(req.getCode())) {
            throw new BadRequestException("Code de permission déjà utilisé : " + req.getCode());
        }
        PermissionEntity p = new PermissionEntity();
        p.setNom(req.getNom());
        p.setCode(req.getCode());
        p.setDescription(req.getDescription());
        p.setModule(req.getModule());
        return PermissionMapper.toDTO(permissionRepository.save(p));
    }

    @Override
    @Transactional
    public PermissionDTO modifierDepuisRequest(String guid, PermissionRequest req) {
        PermissionEntity p = permissionRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", guid));
        permissionRepository.findByCode(req.getCode())
                .filter(other -> !other.getGuid().equals(guid))
                .ifPresent(other -> { throw new BadRequestException("Code de permission déjà utilisé : " + req.getCode()); });
        p.setNom(req.getNom());
        p.setCode(req.getCode());
        p.setDescription(req.getDescription());
        p.setModule(req.getModule());
        return PermissionMapper.toDTO(permissionRepository.save(p));
    }

    @Override
    @Transactional
    public void supprimer(String guid) {
        PermissionEntity p = permissionRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", guid));
        integrityService.checkPermissionDeletable(p);
        permissionRepository.delete(p);
    }
}
