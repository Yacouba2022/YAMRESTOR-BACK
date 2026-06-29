package yamrestor.restor.service.caisse;

import yamrestor.restor.entity.caisse.SessionCaisseEntity;
import yamrestor.restor.enums.StatutSessionCaisse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface SessionCaisseService {
    Page<SessionCaisseEntity> search(StatutSessionCaisse statut, int page, int size);
    SessionCaisseEntity findByGuid(String guid);
    /** Session ouverte d'un caissier (null si aucune). */
    SessionCaisseEntity sessionOuverte(String caissierGuid);
    /** Ouvre une session pour le caissier avec le fond initial donné. */
    SessionCaisseEntity ouvrir(String caissierGuid, BigDecimal fondInitial);
    /** Ferme la session : calcule les totaux encaissés et l'écart de caisse. */
    SessionCaisseEntity fermer(String guid, BigDecimal fondFinalReel, String commentaire);
}
