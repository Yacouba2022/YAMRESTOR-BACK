package yamrestor.restor.service.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.traiteur.MenuTraiteurEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuTraiteurService {
    Page<MenuTraiteurEntity> findAll(int page, int size);
    MenuTraiteurEntity findByGuid(String guid);
    MenuTraiteurEntity save(MenuTraiteurEntity menu);
    void delete(String guid);
    List<MenuTraiteurEntity> findActives();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
