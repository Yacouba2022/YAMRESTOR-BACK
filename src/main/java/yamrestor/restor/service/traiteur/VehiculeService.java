package yamrestor.restor.service.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.traiteur.VehiculeEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehiculeService {
    Page<VehiculeEntity> findAll(int page, int size);
    VehiculeEntity findByGuid(String guid);
    VehiculeEntity save(VehiculeEntity vehicule);
    void delete(String guid);
    List<VehiculeEntity> findActifs();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
