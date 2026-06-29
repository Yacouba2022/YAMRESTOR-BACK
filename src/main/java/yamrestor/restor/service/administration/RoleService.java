package yamrestor.restor.service.administration;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.administration.RoleDTO;
import yamrestor.restor.dto.request.administration.RoleRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    Page<RoleDTO> findAll(int page, int size);
    RoleDTO findByGuid(String guid);
    List<SelectOptionDTO> autocomplete(String q, int limit);
    RoleDTO creerDepuisRequest(RoleRequest req);
    RoleDTO modifierDepuisRequest(String guid, RoleRequest req);
    void supprimer(String guid);
}
