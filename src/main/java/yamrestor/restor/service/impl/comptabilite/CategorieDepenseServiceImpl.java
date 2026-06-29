package yamrestor.restor.service.impl.comptabilite;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.comptabilite.CategorieDepenseEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.comptabilite.CategorieDepenseRepository;
import yamrestor.restor.service.comptabilite.CategorieDepenseService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorieDepenseServiceImpl implements CategorieDepenseService {

    private final CategorieDepenseRepository categorieRepository;

    @Override
    public Page<CategorieDepenseEntity> findAll(int page, int size) {
        return categorieRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public CategorieDepenseEntity findByGuid(String guid) {
        return categorieRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("CategorieDepenseEntity", guid));
    }

    @Override
    @Transactional
    public CategorieDepenseEntity save(CategorieDepenseEntity categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        CategorieDepenseEntity c = findByGuid(guid);
        c.setActif(false);
        categorieRepository.delete(c);
    }

    @Override
    public List<CategorieDepenseEntity> findActives() {
        return categorieRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return categorieRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(c -> SelectOptionDTO.of(c.getGuid(), c.getNom())).toList();
    }
}
