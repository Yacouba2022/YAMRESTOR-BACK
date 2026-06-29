package yamrestor.restor.service.administration;

import yamrestor.restor.dto.administration.ParametreDTO;
import yamrestor.restor.dto.request.administration.ParametreRequest;

public interface ParametreService {
    /** Retourne les paramètres (crée une ligne par défaut si aucune n'existe). */
    ParametreDTO get();

    /** Met à jour les paramètres (les crée si nécessaire). */
    ParametreDTO mettreAJour(ParametreRequest req);
}
