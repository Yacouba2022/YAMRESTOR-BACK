package yamrestor.restor.service.catalogue;

import yamrestor.restor.dto.request.catalogue.RecetteRequest;
import yamrestor.restor.entity.catalogue.RecetteEntity;
import org.springframework.data.domain.Page;

public interface RecetteService {
    Page<RecetteEntity> findAll(int page, int size);
    RecetteEntity findByGuid(String guid);
    /** Recette associée à un produit (404 si le produit n'en a pas). */
    RecetteEntity findByProduit(String produitGuid);
    RecetteEntity creerDepuisRequest(RecetteRequest req);
    RecetteEntity modifierDepuisRequest(String guid, RecetteRequest req);
    void delete(String guid);
}
