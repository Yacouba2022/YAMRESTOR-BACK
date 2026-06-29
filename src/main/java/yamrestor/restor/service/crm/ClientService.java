package yamrestor.restor.service.crm;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.crm.ClientEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {
    Page<ClientEntity> search(String q, int page, int size);
    ClientEntity findByGuid(String guid);
    ClientEntity save(ClientEntity client);
    void delete(String guid);
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
