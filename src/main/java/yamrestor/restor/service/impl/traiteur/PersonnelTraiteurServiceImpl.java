package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.traiteur.PersonnelTraiteurEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.traiteur.PersonnelTraiteurRepository;
import yamrestor.restor.service.traiteur.PersonnelTraiteurService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonnelTraiteurServiceImpl implements PersonnelTraiteurService {

    private final PersonnelTraiteurRepository personnelRepository;

    @Override
    public Page<PersonnelTraiteurEntity> findAll(int page, int size) {
        return personnelRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public PersonnelTraiteurEntity findByGuid(String guid) {
        return personnelRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("PersonnelTraiteurEntity", guid));
    }

    @Override
    @Transactional
    public PersonnelTraiteurEntity save(PersonnelTraiteurEntity personnel) {
        return personnelRepository.save(personnel);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        PersonnelTraiteurEntity p = findByGuid(guid);
        p.setActif(false);
        personnelRepository.delete(p);
    }

    @Override
    public List<PersonnelTraiteurEntity> findActifs() {
        return personnelRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return personnelRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(p -> SelectOptionDTO.of(p.getGuid(), p.getNom())).toList();
    }
}
