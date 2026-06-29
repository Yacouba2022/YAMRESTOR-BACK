package yamrestor.restor.service.caisse;

import yamrestor.restor.dto.caisse.PaiementSituationDTO;
import yamrestor.restor.dto.request.caisse.PaiementRequest;
import yamrestor.restor.entity.caisse.PaiementEntity;
import yamrestor.restor.enums.StatutPaiement;
import org.springframework.data.domain.Page;

public interface PaiementService {
    Page<PaiementEntity> search(StatutPaiement statut, int page, int size);
    PaiementEntity findByGuid(String guid);
    /** Encaisse un paiement ; rattaché à la session ouverte de l'utilisateur si non précisée. */
    PaiementEntity creer(PaiementRequest req, String currentUserGuid);
    PaiementEntity annuler(String guid);
    /** Situation de paiement d'une commande (dû / payé / reste). */
    PaiementSituationDTO situationCommande(String commandeGuid);
}
