package yamrestor.restor.util;

public final class Constants {

    private Constants() {}

    // API
    public static final String API_BASE = "/api/v1";

    // Autocomplete / sélections
    public static final int AUTOCOMPLETE_LIMIT     = 7;
    public static final int AUTOCOMPLETE_MAX_LIMIT = 50;

    // TVA par défaut
    public static final double TVA_RATE = 0.18;

    // Fidélité : valeur monétaire d'un point lors de la conversion en bon d'achat
    public static final java.math.BigDecimal VALEUR_POINT = java.math.BigDecimal.valueOf(10);
    // Validité par défaut d'un bon d'achat (jours)
    public static final int BON_ACHAT_VALIDITE_JOURS = 90;

    // Préfixes des numéros auto-générés
    public static final String PREFIX_COMMANDE   = "CMD";
    public static final String PREFIX_TICKET     = "TKT";
    public static final String PREFIX_FACTURE    = "FAC";
    public static final String PREFIX_RESERVATION = "RES";
    public static final String PREFIX_DEVIS      = "DV";
    public static final String PREFIX_PRESTATION = "PRE";
    public static final String PREFIX_ACHAT      = "CA";
    public static final String PREFIX_PAIEMENT   = "PAI";

    // États utilisateur
    public static final String ETAT_ACTIF   = "actif";
    public static final String ETAT_INACTIF = "inactif";

    // Messages communs
    public static final String MSG_CREATED   = "Créé avec succès";
    public static final String MSG_UPDATED   = "Mis à jour avec succès";
    public static final String MSG_DELETED   = "Supprimé avec succès";
    public static final String MSG_NOT_FOUND = "Ressource introuvable";
}
