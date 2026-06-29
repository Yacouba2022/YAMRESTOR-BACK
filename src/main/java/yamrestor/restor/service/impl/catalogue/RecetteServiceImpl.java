package yamrestor.restor.service.impl.catalogue;

import yamrestor.restor.dto.request.catalogue.RecetteRequest;
import yamrestor.restor.entity.catalogue.MatierePremiereEntity;
import yamrestor.restor.entity.catalogue.ProduitEntity;
import yamrestor.restor.entity.catalogue.RecetteEntity;
import yamrestor.restor.entity.catalogue.RecetteLigneEntity;
import yamrestor.restor.entity.catalogue.UniteEntity;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.catalogue.MatierePremiereRepository;
import yamrestor.restor.repository.catalogue.ProduitRepository;
import yamrestor.restor.repository.catalogue.RecetteRepository;
import yamrestor.restor.repository.catalogue.UniteRepository;
import yamrestor.restor.service.catalogue.RecetteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecetteServiceImpl implements RecetteService {

    private final RecetteRepository recetteRepository;
    private final ProduitRepository produitRepository;
    private final MatierePremiereRepository matiereRepository;
    private final UniteRepository uniteRepository;

    @Override
    public Page<RecetteEntity> findAll(int page, int size) {
        return recetteRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public RecetteEntity findByGuid(String guid) {
        return recetteRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("RecetteEntity", guid));
    }

    @Override
    public RecetteEntity findByProduit(String produitGuid) {
        return recetteRepository.findByProduitGuid(produitGuid)
                .orElseThrow(() -> new ResourceNotFoundException("Recette du produit", produitGuid));
    }

    @Override
    @Transactional
    public RecetteEntity creerDepuisRequest(RecetteRequest req) {
        if (recetteRepository.existsByProduitGuid(req.getProduitGuid())) {
            throw new BadRequestException("Ce produit possède déjà une recette.");
        }
        RecetteEntity r = new RecetteEntity();
        r.setProduit(resolveProduit(req.getProduitGuid()));
        r.setTempsPreparation(req.getTempsPreparation());
        r.setInstructions(req.getInstructions());
        remplacerLignes(r, req);
        return recetteRepository.save(r);
    }

    @Override
    @Transactional
    public RecetteEntity modifierDepuisRequest(String guid, RecetteRequest req) {
        RecetteEntity r = findByGuid(guid);
        r.setTempsPreparation(req.getTempsPreparation());
        r.setInstructions(req.getInstructions());
        remplacerLignes(r, req);
        return recetteRepository.save(r);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        recetteRepository.delete(findByGuid(guid));
    }

    /** Vide les lignes existantes (orphanRemoval) et les reconstruit depuis la requête. */
    private void remplacerLignes(RecetteEntity r, RecetteRequest req) {
        r.getLignes().clear();
        if (req.getLignes() == null) return;
        for (RecetteRequest.LigneRequest l : req.getLignes()) {
            RecetteLigneEntity ligne = new RecetteLigneEntity();
            ligne.setRecette(r);
            ligne.setMatierePremiere(resolveMatiere(l.getMatierePremiereGuid()));
            ligne.setQuantite(l.getQuantite());
            ligne.setUnite(resolveUnite(l.getUniteGuid()));
            r.getLignes().add(ligne);
        }
    }

    private ProduitEntity resolveProduit(String guid) {
        return produitRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", guid));
    }

    private MatierePremiereEntity resolveMatiere(String guid) {
        return matiereRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("MatierePremiere", guid));
    }

    private UniteEntity resolveUnite(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return uniteRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Unite", guid));
    }
}
