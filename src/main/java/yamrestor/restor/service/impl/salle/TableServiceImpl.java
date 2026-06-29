package yamrestor.restor.service.impl.salle;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.request.salle.TableRequest;
import yamrestor.restor.entity.salle.SalleEntity;
import yamrestor.restor.entity.salle.TableEntity;
import yamrestor.restor.enums.StatutTable;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.salle.SalleRepository;
import yamrestor.restor.repository.salle.TableRepository;
import yamrestor.restor.service.salle.TableService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final SalleRepository salleRepository;

    @Override
    public Page<TableEntity> search(String salleGuid, StatutTable statut, int page, int size) {
        String sGuid = (salleGuid != null && !salleGuid.isBlank()) ? salleGuid : null;
        return tableRepository.search(sGuid, statut, PageRequest.of(page, size, Sort.by("numero")));
    }

    @Override
    public TableEntity findByGuid(String guid) {
        return tableRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("TableEntity", guid));
    }

    @Override
    @Transactional
    public TableEntity creerDepuisRequest(TableRequest req) {
        TableEntity t = new TableEntity();
        apply(t, req);
        if (req.getStatut() != null) t.setStatut(req.getStatut());
        return tableRepository.save(t);
    }

    @Override
    @Transactional
    public TableEntity modifierDepuisRequest(String guid, TableRequest req) {
        TableEntity t = findByGuid(guid);
        apply(t, req);
        if (req.getStatut() != null) t.setStatut(req.getStatut());
        return tableRepository.save(t);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        tableRepository.delete(findByGuid(guid));
    }

    @Override
    @Transactional
    public TableEntity changerStatut(String guid, StatutTable statut) {
        TableEntity t = findByGuid(guid);
        t.setStatut(statut);
        return tableRepository.save(t);
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return tableRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(t -> SelectOptionDTO.of(t.getGuid(), "Table " + t.getNumero())).toList();
    }

    private void apply(TableEntity t, TableRequest req) {
        t.setNumero(req.getNumero());
        t.setCapacite(req.getCapacite());
        t.setSalle(resolveSalle(req.getSalleGuid()));
        t.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }

    private SalleEntity resolveSalle(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return salleRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Salle", guid));
    }
}
