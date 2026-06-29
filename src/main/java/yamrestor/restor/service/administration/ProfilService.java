package yamrestor.restor.service.administration;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.administration.ProfilDTO;
import yamrestor.restor.dto.request.administration.ProfilRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProfilService {
    Page<ProfilDTO> findAll(int page, int size);
    ProfilDTO findByGuid(String guid);
    List<SelectOptionDTO> autocomplete(String q, int limit);
    ProfilDTO creerDepuisRequest(ProfilRequest req);
    ProfilDTO modifierDepuisRequest(String guid, ProfilRequest req);
    void supprimer(String guid);
    ProfilDTO dupliquer(String guid);
}
