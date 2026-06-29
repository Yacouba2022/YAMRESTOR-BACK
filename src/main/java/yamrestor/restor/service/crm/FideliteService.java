package yamrestor.restor.service.crm;

import yamrestor.restor.entity.crm.BonAchatEntity;
import yamrestor.restor.entity.crm.ClientEntity;

import java.util.List;

public interface FideliteService {
    /** Crédite des points de fidélité au client. */
    ClientEntity ajouterPoints(String clientGuid, int points);
    /** Débite des points (échoue si solde insuffisant). */
    ClientEntity retirerPoints(String clientGuid, int points);
    /** Convertit des points en bon d'achat (montant = points × valeur du point). */
    BonAchatEntity convertirEnBon(String clientGuid, int points);
    /** Marque un bon d'achat comme utilisé. */
    BonAchatEntity utiliserBon(String bonGuid);
    /** Bons d'achat d'un client. */
    List<BonAchatEntity> bonsClient(String clientGuid);
}
