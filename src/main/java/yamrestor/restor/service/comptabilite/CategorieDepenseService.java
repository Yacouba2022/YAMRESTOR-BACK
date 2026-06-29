package yamrestor.restor.service.comptabilite;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.comptabilite.CategorieDepenseEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategorieDepenseService {
    Page<CategorieDepenseEntity> findAll(int page, int size);
    CategorieDepenseEntity findByGuid(String guid);
    CategorieDepenseEntity save(CategorieDepenseEntity categorie);
    void delete(String guid);
    List<CategorieDepenseEntity> findActives();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
