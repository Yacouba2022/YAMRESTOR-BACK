package yamrestor.restor.service.traiteur;

import yamrestor.restor.dto.request.traiteur.PrestationRequest;
import yamrestor.restor.entity.traiteur.PrestationEntity;
import yamrestor.restor.enums.StatutPrestation;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface PrestationService {
    Page<PrestationEntity> search(StatutPrestation statut, LocalDate date, int page, int size);
    PrestationEntity findByGuid(String guid);
    PrestationEntity creerDepuisRequest(PrestationRequest req);
    PrestationEntity modifierDepuisRequest(String guid, PrestationRequest req);
    PrestationEntity changerStatut(String guid, StatutPrestation statut);
    void delete(String guid);
}
