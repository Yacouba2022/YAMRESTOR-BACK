package yamrestor.restor.service.location;

import yamrestor.restor.dto.request.location.ContratLocationRequest;
import yamrestor.restor.entity.location.ContratLocationEntity;
import yamrestor.restor.enums.StatutContratLocation;
import org.springframework.data.domain.Page;

public interface ContratLocationService {
    Page<ContratLocationEntity> search(StatutContratLocation statut, int page, int size);
    ContratLocationEntity findByGuid(String guid);
    /** Crée le contrat (réservation) en décrémentant la disponibilité du matériel. */
    ContratLocationEntity creerDepuisRequest(ContratLocationRequest req);
    /** Réceptionne le retour : remet le matériel en disponibilité. */
    ContratLocationEntity retourner(String guid);
    /** Annule le contrat : libère le matériel réservé. */
    ContratLocationEntity annuler(String guid);
}
