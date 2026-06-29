package yamrestor.restor.service.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.request.traiteur.MaterielTraiteurRequest;
import yamrestor.restor.entity.traiteur.MaterielTraiteurEntity;
import yamrestor.restor.enums.EtatMateriel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaterielTraiteurService {
    Page<MaterielTraiteurEntity> findAll(int page, int size);
    MaterielTraiteurEntity findByGuid(String guid);
    MaterielTraiteurEntity creerDepuisRequest(MaterielTraiteurRequest req);
    MaterielTraiteurEntity modifierDepuisRequest(String guid, MaterielTraiteurRequest req);
    void delete(String guid);
    /** Signale une perte / casse : retire la quantité du parc (total et disponible). */
    MaterielTraiteurEntity signalerPerteCasse(String guid, int quantite);
    /** Met à jour l'état (entretien). */
    MaterielTraiteurEntity changerEtat(String guid, EtatMateriel etat);
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
