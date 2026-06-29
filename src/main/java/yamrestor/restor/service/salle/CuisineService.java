package yamrestor.restor.service.salle;

import yamrestor.restor.entity.salle.CommandeLigneEntity;
import yamrestor.restor.enums.StatutPreparation;

import java.util.List;

public interface CuisineService {
    /** Lignes à afficher sur l'écran cuisine. Si statut null : toutes les lignes actives (non servies). */
    List<CommandeLigneEntity> lignes(StatutPreparation statut);
    /** Force le statut de préparation d'une ligne. */
    CommandeLigneEntity changerStatut(String ligneGuid, StatutPreparation statut);
    /** Fait avancer la ligne à l'étape suivante (EN_ATTENTE→EN_PREPARATION→PRETE→SERVIE). */
    CommandeLigneEntity avancer(String ligneGuid);
}
