package yamrestor.restor.service.impl.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.request.catalogue.MatierePremiereRequest;
import yamrestor.restor.entity.catalogue.MatierePremiereEntity;
import yamrestor.restor.entity.catalogue.UniteEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.catalogue.MatierePremiereRepository;
import yamrestor.restor.repository.catalogue.UniteRepository;
import yamrestor.restor.service.IntegrityService;
import yamrestor.restor.service.catalogue.MatierePremiereService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatierePremiereServiceImpl implements MatierePremiereService {

    private final MatierePremiereRepository matiereRepository;
    private final UniteRepository uniteRepository;
    private final IntegrityService integrityService;

    @Override
    public Page<MatierePremiereEntity> search(String q, String uniteGuid, Boolean actif, boolean stockFaible, int page, int size) {
        String qNorm = (q != null && !q.isBlank()) ? q.trim() : "";
        String uGuid = (uniteGuid != null && !uniteGuid.isBlank()) ? uniteGuid : null;
        return matiereRepository.search(qNorm, uGuid, actif, stockFaible,
                PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public MatierePremiereEntity findByGuid(String guid) {
        return matiereRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("MatierePremiereEntity", guid));
    }

    @Override
    @Transactional
    public MatierePremiereEntity creerDepuisRequest(MatierePremiereRequest req) {
        MatierePremiereEntity m = new MatierePremiereEntity();
        apply(m, req);
        return matiereRepository.save(m);
    }

    @Override
    @Transactional
    public MatierePremiereEntity modifierDepuisRequest(String guid, MatierePremiereRequest req) {
        MatierePremiereEntity m = findByGuid(guid);
        apply(m, req);
        return matiereRepository.save(m);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        MatierePremiereEntity m = findByGuid(guid);
        integrityService.checkMatiereDeletable(m);
        m.setActif(false);
        matiereRepository.delete(m);
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return matiereRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(m -> SelectOptionDTO.of(m.getGuid(), m.getNom())).toList();
    }

    private void apply(MatierePremiereEntity m, MatierePremiereRequest req) {
        m.setNom(req.getNom());
        m.setCode(req.getCode());
        m.setUnite(resolveUnite(req.getUniteGuid()));
        m.setPrixAchat(req.getPrixAchat());
        m.setStock(req.getStock() != null ? req.getStock() : BigDecimal.ZERO);
        m.setStockMinimum(req.getStockMinimum());
        m.setSeuilAlerte(req.getSeuilAlerte());
        m.setDatePeremption(req.getDatePeremption());
        m.setEmplacement(req.getEmplacement());
        m.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }

    private UniteEntity resolveUnite(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return uniteRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Unite", guid));
    }
}
