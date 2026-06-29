package yamrestor.restor.service.administration;

import yamrestor.restor.dto.administration.PermissionDTO;
import yamrestor.restor.dto.request.administration.PermissionRequest;
import org.springframework.data.domain.Page;

public interface PermissionService {
    Page<PermissionDTO> findAll(int page, int size);
    PermissionDTO findByGuid(String guid);
    PermissionDTO creerDepuisRequest(PermissionRequest req);
    PermissionDTO modifierDepuisRequest(String guid, PermissionRequest req);
    void supprimer(String guid);
}
