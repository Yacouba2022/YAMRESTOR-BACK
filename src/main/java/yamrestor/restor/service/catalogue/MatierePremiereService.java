package yamrestor.restor.service.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.request.catalogue.MatierePremiereRequest;
import yamrestor.restor.entity.catalogue.MatierePremiereEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MatierePremiereService {
    Page<MatierePremiereEntity> search(String q, String uniteGuid, Boolean actif, boolean stockFaible, int page, int size);
    MatierePremiereEntity findByGuid(String guid);
    MatierePremiereEntity creerDepuisRequest(MatierePremiereRequest req);
    MatierePremiereEntity modifierDepuisRequest(String guid, MatierePremiereRequest req);
    void delete(String guid);
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
