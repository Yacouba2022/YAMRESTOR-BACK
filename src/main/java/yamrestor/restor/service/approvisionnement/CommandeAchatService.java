package yamrestor.restor.service.approvisionnement;

import yamrestor.restor.dto.request.approvisionnement.CommandeAchatRequest;
import yamrestor.restor.entity.approvisionnement.CommandeAchatEntity;
import yamrestor.restor.enums.StatutCommandeAchat;
import org.springframework.data.domain.Page;

public interface CommandeAchatService {
    Page<CommandeAchatEntity> search(StatutCommandeAchat statut, int page, int size);
    CommandeAchatEntity findByGuid(String guid);
    CommandeAchatEntity creerDepuisRequest(CommandeAchatRequest req);
    CommandeAchatEntity modifierDepuisRequest(String guid, CommandeAchatRequest req);
    CommandeAchatEntity valider(String guid);
    /** Réceptionne la commande : entrées de stock pour chaque ligne + mise à jour du solde fournisseur. */
    CommandeAchatEntity receptionner(String guid);
    CommandeAchatEntity annuler(String guid);
    void delete(String guid);
}
