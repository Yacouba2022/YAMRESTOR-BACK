package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.traiteur.MenuTraiteurEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.traiteur.MenuTraiteurRepository;
import yamrestor.restor.service.traiteur.MenuTraiteurService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuTraiteurServiceImpl implements MenuTraiteurService {

    private final MenuTraiteurRepository menuRepository;

    @Override
    public Page<MenuTraiteurEntity> findAll(int page, int size) {
        return menuRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public MenuTraiteurEntity findByGuid(String guid) {
        return menuRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("MenuTraiteurEntity", guid));
    }

    @Override
    @Transactional
    public MenuTraiteurEntity save(MenuTraiteurEntity menu) {
        return menuRepository.save(menu);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        MenuTraiteurEntity m = findByGuid(guid);
        m.setActif(false);
        menuRepository.delete(m);
    }

    @Override
    public List<MenuTraiteurEntity> findActives() {
        return menuRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return menuRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(m -> SelectOptionDTO.of(m.getGuid(), m.getNom())).toList();
    }
}
