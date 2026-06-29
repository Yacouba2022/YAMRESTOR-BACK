package yamrestor.restor.service.livraison;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.livraison.ZoneLivraisonEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ZoneLivraisonService {
    Page<ZoneLivraisonEntity> findAll(int page, int size);
    ZoneLivraisonEntity findByGuid(String guid);
    ZoneLivraisonEntity save(ZoneLivraisonEntity zone);
    void delete(String guid);
    List<ZoneLivraisonEntity> findActives();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
