package yamrestor.restor.service.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.request.catalogue.ProduitRequest;
import yamrestor.restor.entity.catalogue.ProduitEntity;
import yamrestor.restor.enums.TypeProduit;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProduitService {
    Page<ProduitEntity> search(String q, String categorieGuid, TypeProduit type, Boolean disponible, Boolean actif, int page, int size);
    ProduitEntity findByGuid(String guid);
    ProduitEntity creerDepuisRequest(ProduitRequest req);
    ProduitEntity modifierDepuisRequest(String guid, ProduitRequest req);
    void delete(String guid);
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
