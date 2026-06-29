package yamrestor.restor.service.salle;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.request.salle.TableRequest;
import yamrestor.restor.entity.salle.TableEntity;
import yamrestor.restor.enums.StatutTable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TableService {
    Page<TableEntity> search(String salleGuid, StatutTable statut, int page, int size);
    TableEntity findByGuid(String guid);
    TableEntity creerDepuisRequest(TableRequest req);
    TableEntity modifierDepuisRequest(String guid, TableRequest req);
    void delete(String guid);
    TableEntity changerStatut(String guid, StatutTable statut);
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
