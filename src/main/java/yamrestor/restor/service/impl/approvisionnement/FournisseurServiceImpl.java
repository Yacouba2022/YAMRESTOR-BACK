package yamrestor.restor.service.impl.approvisionnement;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.approvisionnement.FournisseurEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.approvisionnement.FournisseurRepository;
import yamrestor.restor.service.approvisionnement.FournisseurService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    @Override
    public Page<FournisseurEntity> findAll(int page, int size) {
        return fournisseurRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public FournisseurEntity findByGuid(String guid) {
        return fournisseurRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("FournisseurEntity", guid));
    }

    @Override
    @Transactional
    public FournisseurEntity save(FournisseurEntity fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        FournisseurEntity f = findByGuid(guid);
        f.setActif(false);
        fournisseurRepository.delete(f);
    }

    @Override
    public List<FournisseurEntity> findActifs() {
        return fournisseurRepository.findByActifTrue();
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return fournisseurRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(f -> SelectOptionDTO.of(f.getGuid(), f.getNom())).toList();
    }
}
