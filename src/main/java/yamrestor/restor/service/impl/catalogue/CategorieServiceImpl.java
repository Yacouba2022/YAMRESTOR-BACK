package yamrestor.restor.service.impl.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.catalogue.CategorieEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.catalogue.CategorieRepository;
import yamrestor.restor.service.IntegrityService;
import yamrestor.restor.service.catalogue.CategorieService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final IntegrityService integrityService;

    @Override
    public Page<CategorieEntity> findAll(int page, int size) {
        return categorieRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public CategorieEntity findByGuid(String guid) {
        return categorieRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("CategorieEntity", guid));
    }

    @Override
    @Transactional
    public CategorieEntity save(CategorieEntity categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        CategorieEntity c = findByGuid(guid);
        integrityService.checkCategorieDeletable(c);
        c.setActif(false);
        categorieRepository.delete(c);
    }

    @Override
    public List<CategorieEntity> findActives() {
        return categorieRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return categorieRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(c -> SelectOptionDTO.of(c.getGuid(), c.getNom())).toList();
    }
}
