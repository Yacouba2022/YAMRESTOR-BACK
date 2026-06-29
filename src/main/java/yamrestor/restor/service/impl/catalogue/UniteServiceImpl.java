package yamrestor.restor.service.impl.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.catalogue.UniteEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.catalogue.UniteRepository;
import yamrestor.restor.service.IntegrityService;
import yamrestor.restor.service.catalogue.UniteService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniteServiceImpl implements UniteService {

    private final UniteRepository uniteRepository;
    private final IntegrityService integrityService;

    @Override
    public Page<UniteEntity> findAll(int page, int size) {
        return uniteRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public UniteEntity findByGuid(String guid) {
        return uniteRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("UniteEntity", guid));
    }

    @Override
    @Transactional
    public UniteEntity save(UniteEntity unite) {
        return uniteRepository.save(unite);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        UniteEntity u = findByGuid(guid);
        integrityService.checkUniteDeletable(u);
        u.setActif(false);
        uniteRepository.delete(u);
    }

    @Override
    public List<UniteEntity> findActives() {
        return uniteRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return uniteRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(u -> SelectOptionDTO.of(u.getGuid(), u.getNom())).toList();
    }
}
