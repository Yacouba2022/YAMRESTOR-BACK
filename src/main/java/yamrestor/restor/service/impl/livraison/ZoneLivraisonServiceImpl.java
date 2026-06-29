package yamrestor.restor.service.impl.livraison;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.livraison.ZoneLivraisonEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.livraison.ZoneLivraisonRepository;
import yamrestor.restor.service.livraison.ZoneLivraisonService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneLivraisonServiceImpl implements ZoneLivraisonService {

    private final ZoneLivraisonRepository zoneRepository;

    @Override
    public Page<ZoneLivraisonEntity> findAll(int page, int size) {
        return zoneRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public ZoneLivraisonEntity findByGuid(String guid) {
        return zoneRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("ZoneLivraisonEntity", guid));
    }

    @Override
    @Transactional
    public ZoneLivraisonEntity save(ZoneLivraisonEntity zone) {
        return zoneRepository.save(zone);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        ZoneLivraisonEntity z = findByGuid(guid);
        z.setActif(false);
        zoneRepository.delete(z);
    }

    @Override
    public List<ZoneLivraisonEntity> findActives() {
        return zoneRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return zoneRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(z -> SelectOptionDTO.of(z.getGuid(), z.getNom())).toList();
    }
}
