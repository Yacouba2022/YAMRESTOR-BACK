package yamrestor.restor.service.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.catalogue.UniteEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UniteService {
    Page<UniteEntity> findAll(int page, int size);
    UniteEntity findByGuid(String guid);
    UniteEntity save(UniteEntity unite);
    void delete(String guid);
    List<UniteEntity> findActives();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
