package yamrestor.restor.service.impl.salle;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.salle.SalleEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.salle.SalleRepository;
import yamrestor.restor.service.salle.SalleService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalleServiceImpl implements SalleService {

    private final SalleRepository salleRepository;

    @Override
    public Page<SalleEntity> findAll(int page, int size) {
        return salleRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public SalleEntity findByGuid(String guid) {
        return salleRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("SalleEntity", guid));
    }

    @Override
    @Transactional
    public SalleEntity save(SalleEntity salle) {
        return salleRepository.save(salle);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        SalleEntity s = findByGuid(guid);
        s.setActif(false);
        salleRepository.delete(s);
    }

    @Override
    public List<SalleEntity> findActives() {
        return salleRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return salleRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(s -> SelectOptionDTO.of(s.getGuid(), s.getNom())).toList();
    }
}
