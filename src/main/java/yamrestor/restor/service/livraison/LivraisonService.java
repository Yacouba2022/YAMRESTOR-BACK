package yamrestor.restor.service.livraison;

import yamrestor.restor.dto.request.livraison.LivraisonRequest;
import yamrestor.restor.entity.livraison.LivraisonEntity;
import yamrestor.restor.enums.StatutLivraison;
import org.springframework.data.domain.Page;

public interface LivraisonService {
    Page<LivraisonEntity> search(StatutLivraison statut, int page, int size);
    LivraisonEntity findByGuid(String guid);
    LivraisonEntity creerDepuisRequest(LivraisonRequest req);
    LivraisonEntity modifierDepuisRequest(String guid, LivraisonRequest req);
    LivraisonEntity changerStatut(String guid, StatutLivraison statut);
}
