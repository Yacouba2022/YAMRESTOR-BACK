package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.request.traiteur.MaterielTraiteurRequest;
import yamrestor.restor.entity.traiteur.MaterielTraiteurEntity;
import yamrestor.restor.enums.EtatMateriel;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.traiteur.MaterielTraiteurRepository;
import yamrestor.restor.service.traiteur.MaterielTraiteurService;
import yamrestor.restor.util.AutocompleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterielTraiteurServiceImpl implements MaterielTraiteurService {

    private final MaterielTraiteurRepository materielRepository;

    @Override
    public Page<MaterielTraiteurEntity> findAll(int page, int size) {
        return materielRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public MaterielTraiteurEntity findByGuid(String guid) {
        return materielRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("MaterielTraiteurEntity", guid));
    }

    @Override
    @Transactional
    public MaterielTraiteurEntity creerDepuisRequest(MaterielTraiteurRequest req) {
        int total = req.getQuantiteTotale() != null ? req.getQuantiteTotale() : 0;
        MaterielTraiteurEntity m = new MaterielTraiteurEntity();
        m.setNom(req.getNom());
        m.setCategorie(req.getCategorie());
        m.setQuantiteTotale(total);
        m.setQuantiteDisponible(total);
        m.setEtat(req.getEtat() != null ? req.getEtat() : EtatMateriel.BON);
        m.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
        return materielRepository.save(m);
    }

    @Override
    @Transactional
    public MaterielTraiteurEntity modifierDepuisRequest(String guid, MaterielTraiteurRequest req) {
        MaterielTraiteurEntity m = findByGuid(guid);
        int ancienTotal = m.getQuantiteTotale() != null ? m.getQuantiteTotale() : 0;
        int nouveauTotal = req.getQuantiteTotale() != null ? req.getQuantiteTotale() : ancienTotal;
        int dispo = (m.getQuantiteDisponible() != null ? m.getQuantiteDisponible() : 0) + (nouveauTotal - ancienTotal);
        m.setNom(req.getNom());
        m.setCategorie(req.getCategorie());
        m.setQuantiteTotale(nouveauTotal);
        m.setQuantiteDisponible(Math.max(0, dispo));
        if (req.getEtat() != null) m.setEtat(req.getEtat());
        m.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
        return materielRepository.save(m);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        MaterielTraiteurEntity m = findByGuid(guid);
        m.setActif(false);
        materielRepository.delete(m);
    }

    @Override
    @Transactional
    public MaterielTraiteurEntity signalerPerteCasse(String guid, int quantite) {
        if (quantite <= 0) throw new BadRequestException("La quantité doit être strictement positive.");
        MaterielTraiteurEntity m = findByGuid(guid);
        int total = m.getQuantiteTotale() != null ? m.getQuantiteTotale() : 0;
        int dispo = m.getQuantiteDisponible() != null ? m.getQuantiteDisponible() : 0;
        m.setQuantiteTotale(Math.max(0, total - quantite));
        m.setQuantiteDisponible(Math.max(0, dispo - quantite));
        return materielRepository.save(m);
    }

    @Override
    @Transactional
    public MaterielTraiteurEntity changerEtat(String guid, EtatMateriel etat) {
        MaterielTraiteurEntity m = findByGuid(guid);
        m.setEtat(etat);
        return materielRepository.save(m);
    }

    @Override
    public List<SelectOptionDTO> autocomplete(String q, int limit) {
        return materielRepository.autocomplete(AutocompleteUtil.term(q), AutocompleteUtil.page(limit))
                .stream().map(m -> SelectOptionDTO.of(m.getGuid(), m.getNom())).toList();
    }
}
