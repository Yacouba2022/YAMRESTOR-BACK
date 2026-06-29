package yamrestor.restor.service.comptabilite;

import yamrestor.restor.dto.request.comptabilite.DepenseRequest;
import yamrestor.restor.entity.comptabilite.DepenseEntity;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface DepenseService {
    Page<DepenseEntity> search(LocalDate debut, LocalDate fin, String categorieGuid, int page, int size);
    DepenseEntity findByGuid(String guid);
    DepenseEntity creerDepuisRequest(DepenseRequest req);
    DepenseEntity modifierDepuisRequest(String guid, DepenseRequest req);
    void delete(String guid);
}
