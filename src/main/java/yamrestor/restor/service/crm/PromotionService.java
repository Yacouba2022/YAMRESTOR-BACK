package yamrestor.restor.service.crm;

import yamrestor.restor.dto.request.crm.PromotionRequest;
import yamrestor.restor.entity.crm.PromotionEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionService {
    Page<PromotionEntity> findAll(int page, int size);
    PromotionEntity findByGuid(String guid);
    PromotionEntity creerDepuisRequest(PromotionRequest req);
    PromotionEntity modifierDepuisRequest(String guid, PromotionRequest req);
    void delete(String guid);
    /** Promotions actuellement applicables (période + happy hour). */
    List<PromotionEntity> actives();
    /** Recherche une promotion par code coupon (404 si absent). */
    PromotionEntity findByCode(String code);
}
