package yamrestor.restor.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/** Helpers pour les requêtes d'autocomplete (sélections). */
public final class AutocompleteUtil {

    private AutocompleteUtil() {}

    /** Normalise le terme de recherche (null -> "", trim). */
    public static String term(String q) {
        return q == null ? "" : q.trim();
    }

    /** Première page bornée : limite par défaut 7, plafonnée à AUTOCOMPLETE_MAX_LIMIT. */
    public static Pageable page(int limit) {
        int n = Math.min(Math.max(limit, 1), Constants.AUTOCOMPLETE_MAX_LIMIT);
        return PageRequest.of(0, n);
    }
}
