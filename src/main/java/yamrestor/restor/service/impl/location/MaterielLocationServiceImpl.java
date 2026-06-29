package yamrestor.restor.service.impl.location;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.location.MaterielLocationEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.location.MaterielLocationRepository;
import yamrestor.restor.service.location.MaterielLocationService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterielLocationServiceImpl implements MaterielLocationService {

    private final MaterielLocationRepository materielRepository;

    @Override
    public Page<MaterielLocationEntity> findAll(int page, int size) {
        return materielRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public MaterielLocationEntity findByGuid(String guid) {
        return materielRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("MaterielLocationEntity", guid));
    }

    @Override
    @Transactional
    public MaterielLocationEntity save(MaterielLocationEntity materiel) {
        return materielRepository.save(materiel);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        MaterielLocationEntity m = findByGuid(guid);
        m.setActif(false);
        materielRepository.delete(m);
    }

    @Override
    public List<MaterielLocationEntity> findActifs() {
        return materielRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return materielRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(m -> SelectOptionDTO.of(m.getGuid(), m.getNom())).toList();
    }
}
