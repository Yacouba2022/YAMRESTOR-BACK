package yamrestor.restor.service.salle;

import yamrestor.restor.dto.request.salle.CommandeRequest;
import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.enums.StatutCommande;
import yamrestor.restor.enums.TypeCommande;
import org.springframework.data.domain.Page;

public interface CommandeService {
    Page<CommandeEntity> search(StatutCommande statut, TypeCommande type, int page, int size);
    CommandeEntity findByGuid(String guid);
    CommandeEntity creerDepuisRequest(CommandeRequest req);
    CommandeEntity modifierDepuisRequest(String guid, CommandeRequest req);
    /** Envoie la commande en cuisine (EN_COURS → ENVOYEE). */
    CommandeEntity envoyer(String guid);
    /** Annule la commande et libère la table. */
    CommandeEntity annuler(String guid);
    void delete(String guid);
}
