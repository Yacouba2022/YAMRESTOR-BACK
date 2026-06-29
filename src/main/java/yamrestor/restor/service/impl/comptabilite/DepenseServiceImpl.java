package yamrestor.restor.service.impl.comptabilite;

import yamrestor.restor.dto.request.comptabilite.DepenseRequest;
import yamrestor.restor.entity.comptabilite.CategorieDepenseEntity;
import yamrestor.restor.entity.comptabilite.DepenseEntity;
import yamrestor.restor.enums.ModePaiement;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.comptabilite.CategorieDepenseRepository;
import yamrestor.restor.repository.comptabilite.DepenseRepository;
import yamrestor.restor.service.comptabilite.DepenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DepenseServiceImpl implements DepenseService {

    private final DepenseRepository depenseRepository;
    private final CategorieDepenseRepository categorieRepository;

    @Override
    public Page<DepenseEntity> search(LocalDate debut, LocalDate fin, String categorieGuid, int page, int size) {
        String catGuid = (categorieGuid != null && !categorieGuid.isBlank()) ? categorieGuid : null;
        return depenseRepository.search(debut, fin, catGuid, PageRequest.of(page, size));
    }

    @Override
    public DepenseEntity findByGuid(String guid) {
        return depenseRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("DepenseEntity", guid));
    }

    @Override
    @Transactional
    public DepenseEntity creerDepuisRequest(DepenseRequest req) {
        DepenseEntity d = new DepenseEntity();
        apply(d, req);
        return depenseRepository.save(d);
    }

    @Override
    @Transactional
    public DepenseEntity modifierDepuisRequest(String guid, DepenseRequest req) {
        DepenseEntity d = findByGuid(guid);
        apply(d, req);
        return depenseRepository.save(d);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        depenseRepository.delete(findByGuid(guid));
    }

    private void apply(DepenseEntity d, DepenseRequest req) {
        d.setLibelle(req.getLibelle());
        d.setCategorie(resolveCategorie(req.getCategorieGuid()));
        d.setMontant(req.getMontant());
        d.setDateDepense(req.getDateDepense());
        d.setMode(req.getMode() != null ? req.getMode() : ModePaiement.ESPECES);
        d.setDescription(req.getDescription());
    }

    private CategorieDepenseEntity resolveCategorie(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return categorieRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("CategorieDepense", guid));
    }
}
