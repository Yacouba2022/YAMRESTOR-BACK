package yamrestor.restor.service.impl.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.request.catalogue.ProduitRequest;
import yamrestor.restor.entity.catalogue.CategorieEntity;
import yamrestor.restor.entity.catalogue.ProduitEntity;
import yamrestor.restor.enums.TypeProduit;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.catalogue.CategorieRepository;
import yamrestor.restor.repository.catalogue.ProduitRepository;
import yamrestor.restor.service.IntegrityService;
import yamrestor.restor.service.catalogue.ProduitService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final IntegrityService integrityService;

    @Override
    public Page<ProduitEntity> search(String q, String categorieGuid, TypeProduit type, Boolean disponible, Boolean actif, int page, int size) {
        String qNorm = (q != null && !q.isBlank()) ? q.trim() : "";
        String catGuid = (categorieGuid != null && !categorieGuid.isBlank()) ? categorieGuid : null;
        return produitRepository.search(qNorm, catGuid, type, disponible, actif,
                PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public ProduitEntity findByGuid(String guid) {
        return produitRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("ProduitEntity", guid));
    }

    @Override
    @Transactional
    public ProduitEntity creerDepuisRequest(ProduitRequest req) {
        ProduitEntity p = new ProduitEntity();
        apply(p, req);
        return produitRepository.save(p);
    }

    @Override
    @Transactional
    public ProduitEntity modifierDepuisRequest(String guid, ProduitRequest req) {
        ProduitEntity p = findByGuid(guid);
        apply(p, req);
        return produitRepository.save(p);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        ProduitEntity p = findByGuid(guid);
        integrityService.checkProduitDeletable(p);
        p.setActif(false);
        produitRepository.delete(p);
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return produitRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(p -> SelectOptionDTO.of(p.getGuid(), p.getNom())).toList();
    }

    private void apply(ProduitEntity p, ProduitRequest req) {
        p.setNom(req.getNom());
        p.setCode(req.getCode());
        p.setDescription(req.getDescription());
        p.setCategorie(resolveCategorie(req.getCategorieGuid()));
        if (req.getType() != null) p.setType(req.getType());
        p.setPrixVente(req.getPrixVente());
        p.setTauxTva(req.getTauxTva());
        p.setImage(req.getImage());
        p.setDisponible(req.getDisponible() != null ? req.getDisponible() : Boolean.TRUE);
        p.setTempsPreparation(req.getTempsPreparation());
        p.setSaisonnier(req.getSaisonnier() != null ? req.getSaisonnier() : Boolean.FALSE);
        p.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }

    private CategorieEntity resolveCategorie(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return categorieRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie", guid));
    }
}
