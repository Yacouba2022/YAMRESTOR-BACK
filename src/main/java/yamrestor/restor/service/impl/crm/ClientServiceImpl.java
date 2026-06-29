package yamrestor.restor.service.impl.crm;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.crm.ClientEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.crm.ClientRepository;
import yamrestor.restor.service.crm.ClientService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Page<ClientEntity> search(String q, int page, int size) {
        String qNorm = (q != null && !q.isBlank()) ? q.trim() : "";
        return clientRepository.search(qNorm, PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public ClientEntity findByGuid(String guid) {
        return clientRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("ClientEntity", guid));
    }

    @Override
    @Transactional
    public ClientEntity save(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        ClientEntity c = findByGuid(guid);
        c.setActif(false);
        clientRepository.delete(c);
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return clientRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(c -> SelectOptionDTO.of(c.getGuid(), c.getNom())).toList();
    }
}
