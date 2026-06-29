package yamrestor.restor.service.traiteur;

import yamrestor.restor.dto.request.traiteur.PaiementPrestationRequest;
import yamrestor.restor.entity.traiteur.PaiementPrestationEntity;

import java.util.List;

public interface PaiementPrestationService {
    PaiementPrestationEntity findByGuid(String guid);
    List<PaiementPrestationEntity> paiementsPrestation(String prestationGuid);
    /** Encaisse un acompte / solde et met à jour le montant payé de la prestation. */
    PaiementPrestationEntity creer(PaiementPrestationRequest req);
    /** Annule le paiement et décrémente le montant payé de la prestation. */
    PaiementPrestationEntity annuler(String guid);
}
