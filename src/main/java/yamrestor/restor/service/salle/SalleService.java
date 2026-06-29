package yamrestor.restor.service.salle;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.salle.SalleEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SalleService {
    Page<SalleEntity> findAll(int page, int size);
    SalleEntity findByGuid(String guid);
    SalleEntity save(SalleEntity salle);
    void delete(String guid);
    List<SalleEntity> findActives();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
