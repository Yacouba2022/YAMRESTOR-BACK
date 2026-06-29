package yamrestor.restor.service.location;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.location.MaterielLocationEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaterielLocationService {
    Page<MaterielLocationEntity> findAll(int page, int size);
    MaterielLocationEntity findByGuid(String guid);
    MaterielLocationEntity save(MaterielLocationEntity materiel);
    void delete(String guid);
    List<MaterielLocationEntity> findActifs();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
