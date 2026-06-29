package yamrestor.restor.service.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.catalogue.CategorieEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategorieService {
    Page<CategorieEntity> findAll(int page, int size);
    CategorieEntity findByGuid(String guid);
    CategorieEntity save(CategorieEntity categorie);
    void delete(String guid);
    List<CategorieEntity> findActives();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
