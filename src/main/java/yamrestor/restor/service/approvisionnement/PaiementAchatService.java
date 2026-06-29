package yamrestor.restor.service.approvisionnement;

import yamrestor.restor.dto.request.approvisionnement.PaiementAchatRequest;
import yamrestor.restor.entity.approvisionnement.PaiementAchatEntity;
import yamrestor.restor.enums.StatutPaiement;
import org.springframework.data.domain.Page;

public interface PaiementAchatService {
    Page<PaiementAchatEntity> search(StatutPaiement statut, int page, int size);
    PaiementAchatEntity findByGuid(String guid);
    /** Enregistre un paiement fournisseur et diminue le solde dû. */
    PaiementAchatEntity creer(PaiementAchatRequest req);
    /** Annule le paiement et réintègre le montant au solde dû. */
    PaiementAchatEntity annuler(String guid);
}
