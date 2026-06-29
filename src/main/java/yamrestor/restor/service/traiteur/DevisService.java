package yamrestor.restor.service.traiteur;

import yamrestor.restor.dto.request.traiteur.DevisRequest;
import yamrestor.restor.entity.traiteur.ContratEntity;
import yamrestor.restor.entity.traiteur.DevisEntity;
import yamrestor.restor.enums.StatutDevis;
import org.springframework.data.domain.Page;

public interface DevisService {
    Page<DevisEntity> search(StatutDevis statut, int page, int size);
    DevisEntity findByGuid(String guid);
    DevisEntity creerDepuisRequest(DevisRequest req);
    DevisEntity modifierDepuisRequest(String guid, DevisRequest req);
    DevisEntity valider(String guid);
    /** Convertit le devis validé en contrat ; confirme la prestation et fixe son montant. */
    ContratEntity convertirEnContrat(String guid);
    void delete(String guid);
}
