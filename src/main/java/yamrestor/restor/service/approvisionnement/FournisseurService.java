package yamrestor.restor.service.approvisionnement;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.approvisionnement.FournisseurEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FournisseurService {
    Page<FournisseurEntity> findAll(int page, int size);
    FournisseurEntity findByGuid(String guid);
    FournisseurEntity save(FournisseurEntity fournisseur);
    void delete(String guid);
    List<FournisseurEntity> findActifs();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
