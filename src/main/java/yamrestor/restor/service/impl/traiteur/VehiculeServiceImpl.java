package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.traiteur.VehiculeEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.traiteur.VehiculeRepository;
import yamrestor.restor.service.traiteur.VehiculeService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public Page<VehiculeEntity> findAll(int page, int size) {
        return vehiculeRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public VehiculeEntity findByGuid(String guid) {
        return vehiculeRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("VehiculeEntity", guid));
    }

    @Override
    @Transactional
    public VehiculeEntity save(VehiculeEntity vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        VehiculeEntity v = findByGuid(guid);
        v.setActif(false);
        vehiculeRepository.delete(v);
    }

    @Override
    public List<VehiculeEntity> findActifs() {
        return vehiculeRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return vehiculeRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(v -> SelectOptionDTO.of(v.getGuid(), v.getNom())).toList();
    }
}
