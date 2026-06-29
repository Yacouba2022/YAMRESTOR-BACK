package yamrestor.restor.util;

import java.util.List;

/**
 * Catalogue centralisé des permissions de l'application (codes en kebab-case
 * « action-module »). Les constantes sont référencées par {@code @PreAuthorize}
 * sur les contrôleurs ; le {@code CATALOG} est synchronisé en base au démarrage
 * par {@code PermissionSeeder} et agrégé dans le rôle Super Admin par {@code AdminSeeder}.
 *
 * <p>Chaque phase d'implémentation ajoute ici les permissions de ses modules.</p>
 */
public final class Permissions {

    private Permissions() {}

    /** Définition d'une permission : code unique, libellé affiché, module de regroupement. */
    public record Def(String code, String libelle, String module) {}

    // ─── Module 2 : Administration / Utilisateurs ─────────────────────────────
    public static final String USER_CONSULTER          = "consulter-utilisateurs";
    public static final String USER_CREER              = "creer-utilisateurs";
    public static final String USER_MODIFIER           = "modifier-utilisateurs";
    public static final String USER_SUPPRIMER          = "supprimer-utilisateurs";
    public static final String USER_CHANGER_MOT_DE_PASSE = "changer-mot-de-passe-utilisateurs";
    public static final String USER_DECONNECTER        = "deconnecter-utilisateurs";
    public static final String USER_ACTIVER_DESACTIVER = "activer-desactiver-utilisateurs";

    public static final String PROFIL_CONSULTER = "consulter-profils";
    public static final String PROFIL_CREER     = "creer-profils";
    public static final String PROFIL_MODIFIER  = "modifier-profils";
    public static final String PROFIL_SUPPRIMER = "supprimer-profils";
    public static final String PROFIL_DUPLIQUER = "dupliquer-profils";

    public static final String ROLE_CONSULTER = "consulter-roles";
    public static final String ROLE_CREER     = "creer-roles";
    public static final String ROLE_MODIFIER  = "modifier-roles";
    public static final String ROLE_SUPPRIMER = "supprimer-roles";

    public static final String PERMISSION_CONSULTER = "consulter-permissions";
    public static final String PERMISSION_CREER     = "creer-permissions";
    public static final String PERMISSION_MODIFIER  = "modifier-permissions";
    public static final String PERMISSION_SUPPRIMER = "supprimer-permissions";

    public static final String PARAMETRE_CONSULTER = "consulter-parametres";
    public static final String PARAMETRE_GERER     = "gerer-parametres";

    // ─── Module 27 : Imprimantes ──────────────────────────────────────────────
    public static final String IMPRIMANTE_CONSULTER = "consulter-imprimantes";
    public static final String IMPRIMANTE_CREER     = "creer-imprimantes";
    public static final String IMPRIMANTE_MODIFIER  = "modifier-imprimantes";
    public static final String IMPRIMANTE_SUPPRIMER = "supprimer-imprimantes";

    // ─── Module 3 : Catégories ────────────────────────────────────────────────
    public static final String CATEGORIE_CONSULTER = "consulter-categories";
    public static final String CATEGORIE_CREER     = "creer-categories";
    public static final String CATEGORIE_MODIFIER  = "modifier-categories";
    public static final String CATEGORIE_SUPPRIMER = "supprimer-categories";

    // ─── Référentiel : Unités ─────────────────────────────────────────────────
    public static final String UNITE_CONSULTER = "consulter-unites";
    public static final String UNITE_CREER     = "creer-unites";
    public static final String UNITE_MODIFIER  = "modifier-unites";
    public static final String UNITE_SUPPRIMER = "supprimer-unites";

    // ─── Module 6 : Matières premières ────────────────────────────────────────
    public static final String MATIERE_CONSULTER = "consulter-matieres-premieres";
    public static final String MATIERE_CREER     = "creer-matieres-premieres";
    public static final String MATIERE_MODIFIER  = "modifier-matieres-premieres";
    public static final String MATIERE_SUPPRIMER = "supprimer-matieres-premieres";

    // ─── Module 4 : Produits ──────────────────────────────────────────────────
    public static final String PRODUIT_CONSULTER = "consulter-produits";
    public static final String PRODUIT_CREER     = "creer-produits";
    public static final String PRODUIT_MODIFIER  = "modifier-produits";
    public static final String PRODUIT_SUPPRIMER = "supprimer-produits";

    // ─── Module 5 : Recettes ──────────────────────────────────────────────────
    public static final String RECETTE_CONSULTER = "consulter-recettes";
    public static final String RECETTE_CREER     = "creer-recettes";
    public static final String RECETTE_MODIFIER  = "modifier-recettes";
    public static final String RECETTE_SUPPRIMER = "supprimer-recettes";

    // ─── Module 11 : Salles ───────────────────────────────────────────────────
    public static final String SALLE_CONSULTER = "consulter-salles";
    public static final String SALLE_CREER     = "creer-salles";
    public static final String SALLE_MODIFIER  = "modifier-salles";
    public static final String SALLE_SUPPRIMER = "supprimer-salles";

    // ─── Module 10 : Tables ───────────────────────────────────────────────────
    public static final String TABLE_CONSULTER       = "consulter-tables";
    public static final String TABLE_CREER           = "creer-tables";
    public static final String TABLE_MODIFIER        = "modifier-tables";
    public static final String TABLE_SUPPRIMER       = "supprimer-tables";
    public static final String TABLE_CHANGER_STATUT  = "changer-statut-tables";

    // ─── Module 12 : Réservations ─────────────────────────────────────────────
    public static final String RESERVATION_CONSULTER = "consulter-reservations";
    public static final String RESERVATION_CREER     = "creer-reservations";
    public static final String RESERVATION_MODIFIER  = "modifier-reservations";
    public static final String RESERVATION_SUPPRIMER = "supprimer-reservations";
    public static final String RESERVATION_CONFIRMER = "confirmer-reservations";
    public static final String RESERVATION_ANNULER   = "annuler-reservations";

    // ─── Module 13 : Commandes ────────────────────────────────────────────────
    public static final String COMMANDE_CONSULTER = "consulter-commandes";
    public static final String COMMANDE_CREER     = "creer-commandes";
    public static final String COMMANDE_MODIFIER  = "modifier-commandes";
    public static final String COMMANDE_SUPPRIMER = "supprimer-commandes";
    public static final String COMMANDE_ENVOYER   = "envoyer-commandes";
    public static final String COMMANDE_ANNULER   = "annuler-commandes";

    // ─── Module 14 : Cuisine (KDS) ────────────────────────────────────────────
    public static final String CUISINE_CONSULTER      = "consulter-cuisine";
    public static final String CUISINE_CHANGER_STATUT = "changer-statut-cuisine";

    // ─── Module 16 : Caisse / Sessions de caisse ──────────────────────────────
    public static final String SESSION_CAISSE_CONSULTER = "consulter-sessions-caisse";
    public static final String SESSION_CAISSE_OUVRIR    = "ouvrir-sessions-caisse";
    public static final String SESSION_CAISSE_FERMER    = "fermer-sessions-caisse";

    // ─── Module 17 : Paiements ────────────────────────────────────────────────
    public static final String PAIEMENT_CONSULTER = "consulter-paiements";
    public static final String PAIEMENT_CREER     = "creer-paiements";
    public static final String PAIEMENT_ANNULER   = "annuler-paiements";

    // ─── Module 18 : Livraison ────────────────────────────────────────────────
    public static final String ZONE_LIVRAISON_CONSULTER = "consulter-zones-livraison";
    public static final String ZONE_LIVRAISON_CREER     = "creer-zones-livraison";
    public static final String ZONE_LIVRAISON_MODIFIER  = "modifier-zones-livraison";
    public static final String ZONE_LIVRAISON_SUPPRIMER = "supprimer-zones-livraison";

    public static final String LIVRAISON_CONSULTER      = "consulter-livraisons";
    public static final String LIVRAISON_CREER          = "creer-livraisons";
    public static final String LIVRAISON_MODIFIER       = "modifier-livraisons";
    public static final String LIVRAISON_CHANGER_STATUT = "changer-statut-livraisons";

    // ─── Module 7 : Fournisseurs ──────────────────────────────────────────────
    public static final String FOURNISSEUR_CONSULTER = "consulter-fournisseurs";
    public static final String FOURNISSEUR_CREER     = "creer-fournisseurs";
    public static final String FOURNISSEUR_MODIFIER  = "modifier-fournisseurs";
    public static final String FOURNISSEUR_SUPPRIMER = "supprimer-fournisseurs";

    // ─── Module 8 : Achats ────────────────────────────────────────────────────
    public static final String COMMANDE_ACHAT_CONSULTER     = "consulter-commandes-achat";
    public static final String COMMANDE_ACHAT_CREER         = "creer-commandes-achat";
    public static final String COMMANDE_ACHAT_MODIFIER      = "modifier-commandes-achat";
    public static final String COMMANDE_ACHAT_SUPPRIMER     = "supprimer-commandes-achat";
    public static final String COMMANDE_ACHAT_VALIDER       = "valider-commandes-achat";
    public static final String COMMANDE_ACHAT_RECEPTIONNER  = "receptionner-commandes-achat";
    public static final String COMMANDE_ACHAT_ANNULER       = "annuler-commandes-achat";

    public static final String PAIEMENT_ACHAT_CONSULTER = "consulter-paiements-achat";
    public static final String PAIEMENT_ACHAT_CREER     = "creer-paiements-achat";
    public static final String PAIEMENT_ACHAT_ANNULER   = "annuler-paiements-achat";

    // ─── Module 9 : Stock ─────────────────────────────────────────────────────
    public static final String STOCK_CONSULTER           = "consulter-stock";
    public static final String STOCK_MOUVEMENTER         = "mouvementer-stock";   // entrée / sortie / ajustement
    public static final String MOUVEMENT_STOCK_CONSULTER = "consulter-mouvements-stock";

    // ─── Module 19 : Clients ──────────────────────────────────────────────────
    public static final String CLIENT_CONSULTER = "consulter-clients";
    public static final String CLIENT_CREER     = "creer-clients";
    public static final String CLIENT_MODIFIER  = "modifier-clients";
    public static final String CLIENT_SUPPRIMER = "supprimer-clients";

    // ─── Module 20 : Promotions ───────────────────────────────────────────────
    public static final String PROMOTION_CONSULTER = "consulter-promotions";
    public static final String PROMOTION_CREER     = "creer-promotions";
    public static final String PROMOTION_MODIFIER  = "modifier-promotions";
    public static final String PROMOTION_SUPPRIMER = "supprimer-promotions";

    // ─── Module 21 : Fidélité ─────────────────────────────────────────────────
    public static final String FIDELITE_CONSULTER = "consulter-fidelite";
    public static final String FIDELITE_GERER     = "gerer-fidelite";   // points, bons d'achat

    // ─── Module 22 : Dépenses ─────────────────────────────────────────────────
    public static final String CATEGORIE_DEPENSE_CONSULTER = "consulter-categories-depense";
    public static final String CATEGORIE_DEPENSE_CREER     = "creer-categories-depense";
    public static final String CATEGORIE_DEPENSE_MODIFIER  = "modifier-categories-depense";
    public static final String CATEGORIE_DEPENSE_SUPPRIMER = "supprimer-categories-depense";

    public static final String DEPENSE_CONSULTER = "consulter-depenses";
    public static final String DEPENSE_CREER     = "creer-depenses";
    public static final String DEPENSE_MODIFIER  = "modifier-depenses";
    public static final String DEPENSE_SUPPRIMER = "supprimer-depenses";

    // ─── Module 23 : Comptabilité ─────────────────────────────────────────────
    public static final String COMPTABILITE_CONSULTER = "consulter-comptabilite";

    // ─── Module 24 : Rapports ─────────────────────────────────────────────────
    public static final String RAPPORT_CONSULTER = "consulter-rapports";

    // ─── Module 1 : Tableau de bord ───────────────────────────────────────────
    public static final String DASHBOARD_CONSULTER = "consulter-dashboard";

    // ─── Module 25 : Notifications ────────────────────────────────────────────
    public static final String NOTIFICATION_CONSULTER = "consulter-notifications";

    // ─── Module 28 : Traiteur ─────────────────────────────────────────────────
    public static final String MENU_TRAITEUR_CONSULTER = "consulter-menus-traiteur";
    public static final String MENU_TRAITEUR_CREER     = "creer-menus-traiteur";
    public static final String MENU_TRAITEUR_MODIFIER  = "modifier-menus-traiteur";
    public static final String MENU_TRAITEUR_SUPPRIMER = "supprimer-menus-traiteur";

    public static final String PRESTATION_CONSULTER = "consulter-prestations";
    public static final String PRESTATION_CREER     = "creer-prestations";
    public static final String PRESTATION_MODIFIER  = "modifier-prestations";
    public static final String PRESTATION_SUPPRIMER = "supprimer-prestations";
    public static final String PRESTATION_CHANGER_STATUT = "changer-statut-prestations";

    public static final String DEVIS_CONSULTER = "consulter-devis";
    public static final String DEVIS_CREER     = "creer-devis";
    public static final String DEVIS_MODIFIER  = "modifier-devis";
    public static final String DEVIS_SUPPRIMER = "supprimer-devis";
    public static final String DEVIS_VALIDER   = "valider-devis";
    public static final String DEVIS_CONVERTIR = "convertir-devis";

    public static final String CONTRAT_CONSULTER = "consulter-contrats";
    public static final String CONTRAT_SIGNER    = "signer-contrats";
    public static final String CONTRAT_ANNULER   = "annuler-contrats";

    public static final String PAIEMENT_PRESTATION_CONSULTER = "consulter-paiements-prestation";
    public static final String PAIEMENT_PRESTATION_CREER     = "creer-paiements-prestation";
    public static final String PAIEMENT_PRESTATION_ANNULER   = "annuler-paiements-prestation";

    // ─── Module 29 : Location de matériel ─────────────────────────────────────
    public static final String MATERIEL_LOCATION_CONSULTER = "consulter-materiels-location";
    public static final String MATERIEL_LOCATION_CREER     = "creer-materiels-location";
    public static final String MATERIEL_LOCATION_MODIFIER  = "modifier-materiels-location";
    public static final String MATERIEL_LOCATION_SUPPRIMER = "supprimer-materiels-location";

    public static final String CONTRAT_LOCATION_CONSULTER = "consulter-contrats-location";
    public static final String CONTRAT_LOCATION_CREER     = "creer-contrats-location";
    public static final String CONTRAT_LOCATION_MODIFIER  = "modifier-contrats-location";
    public static final String CONTRAT_LOCATION_RETOURNER = "retourner-contrats-location";
    public static final String CONTRAT_LOCATION_ANNULER   = "annuler-contrats-location";

    // ─── Module 30 : Évènements ───────────────────────────────────────────────
    public static final String EVENEMENT_CONSULTER = "consulter-evenements";

    // ─── Module 28b : Ressources traiteur ─────────────────────────────────────
    public static final String PERSONNEL_TRAITEUR_CONSULTER = "consulter-personnel-traiteur";
    public static final String PERSONNEL_TRAITEUR_CREER     = "creer-personnel-traiteur";
    public static final String PERSONNEL_TRAITEUR_MODIFIER  = "modifier-personnel-traiteur";
    public static final String PERSONNEL_TRAITEUR_SUPPRIMER = "supprimer-personnel-traiteur";

    public static final String VEHICULE_CONSULTER = "consulter-vehicules";
    public static final String VEHICULE_CREER     = "creer-vehicules";
    public static final String VEHICULE_MODIFIER  = "modifier-vehicules";
    public static final String VEHICULE_SUPPRIMER = "supprimer-vehicules";

    public static final String MATERIEL_TRAITEUR_CONSULTER = "consulter-materiel-traiteur";
    public static final String MATERIEL_TRAITEUR_CREER     = "creer-materiel-traiteur";
    public static final String MATERIEL_TRAITEUR_MODIFIER  = "modifier-materiel-traiteur";
    public static final String MATERIEL_TRAITEUR_SUPPRIMER = "supprimer-materiel-traiteur";

    public static final String AFFECTATION_CONSULTER = "consulter-affectations";
    public static final String AFFECTATION_GERER     = "gerer-affectations";

    public static final String RAPPORT_TRAITEUR_CONSULTER = "consulter-rapports-traiteur";

    /**
     * Catalogue complet. À enrichir module par module au fil des phases.
     */
    public static final List<Def> CATALOG = List.of(
            new Def(USER_CONSULTER, "Consulter les utilisateurs", "utilisateur"),
            new Def(USER_CREER,     "Créer un utilisateur",        "utilisateur"),
            new Def(USER_MODIFIER,  "Modifier un utilisateur",     "utilisateur"),
            new Def(USER_SUPPRIMER, "Supprimer un utilisateur",    "utilisateur"),
            new Def(USER_CHANGER_MOT_DE_PASSE, "Changer le mot de passe d'un utilisateur", "utilisateur"),
            new Def(USER_DECONNECTER, "Déconnecter un utilisateur", "utilisateur"),
            new Def(USER_ACTIVER_DESACTIVER, "Activer / désactiver un utilisateur", "utilisateur"),

            new Def(PROFIL_CONSULTER, "Consulter les profils", "profil"),
            new Def(PROFIL_CREER,     "Créer un profil",       "profil"),
            new Def(PROFIL_MODIFIER,  "Modifier un profil",    "profil"),
            new Def(PROFIL_SUPPRIMER, "Supprimer un profil",   "profil"),
            new Def(PROFIL_DUPLIQUER, "Dupliquer un profil",   "profil"),

            new Def(ROLE_CONSULTER, "Consulter les rôles", "role"),
            new Def(ROLE_CREER,     "Créer un rôle",       "role"),
            new Def(ROLE_MODIFIER,  "Modifier un rôle",    "role"),
            new Def(ROLE_SUPPRIMER, "Supprimer un rôle",   "role"),

            new Def(PERMISSION_CONSULTER, "Consulter les permissions", "permission"),
            new Def(PERMISSION_CREER,     "Créer une permission",      "permission"),
            new Def(PERMISSION_MODIFIER,  "Modifier une permission",   "permission"),
            new Def(PERMISSION_SUPPRIMER, "Supprimer une permission",  "permission"),

            new Def(PARAMETRE_CONSULTER, "Consulter les paramètres", "parametre"),
            new Def(PARAMETRE_GERER,     "Gérer les paramètres",     "parametre"),

            new Def(CATEGORIE_CONSULTER, "Consulter les catégories", "categorie"),
            new Def(CATEGORIE_CREER,     "Créer une catégorie",      "categorie"),
            new Def(CATEGORIE_MODIFIER,  "Modifier une catégorie",   "categorie"),
            new Def(CATEGORIE_SUPPRIMER, "Supprimer une catégorie",  "categorie"),

            new Def(IMPRIMANTE_CONSULTER, "Consulter les imprimantes", "imprimante"),
            new Def(IMPRIMANTE_CREER,     "Créer une imprimante",      "imprimante"),
            new Def(IMPRIMANTE_MODIFIER,  "Modifier une imprimante",   "imprimante"),
            new Def(IMPRIMANTE_SUPPRIMER, "Supprimer une imprimante",  "imprimante"),

            new Def(UNITE_CONSULTER, "Consulter les unités", "unite"),
            new Def(UNITE_CREER,     "Créer une unité",      "unite"),
            new Def(UNITE_MODIFIER,  "Modifier une unité",   "unite"),
            new Def(UNITE_SUPPRIMER, "Supprimer une unité",  "unite"),

            new Def(MATIERE_CONSULTER, "Consulter les matières premières", "matiere_premiere"),
            new Def(MATIERE_CREER,     "Créer une matière première",       "matiere_premiere"),
            new Def(MATIERE_MODIFIER,  "Modifier une matière première",    "matiere_premiere"),
            new Def(MATIERE_SUPPRIMER, "Supprimer une matière première",   "matiere_premiere"),

            new Def(PRODUIT_CONSULTER, "Consulter les produits", "produit"),
            new Def(PRODUIT_CREER,     "Créer un produit",       "produit"),
            new Def(PRODUIT_MODIFIER,  "Modifier un produit",    "produit"),
            new Def(PRODUIT_SUPPRIMER, "Supprimer un produit",   "produit"),

            new Def(RECETTE_CONSULTER, "Consulter les recettes", "recette"),
            new Def(RECETTE_CREER,     "Créer une recette",      "recette"),
            new Def(RECETTE_MODIFIER,  "Modifier une recette",   "recette"),
            new Def(RECETTE_SUPPRIMER, "Supprimer une recette",  "recette"),

            new Def(SALLE_CONSULTER, "Consulter les salles", "salle"),
            new Def(SALLE_CREER,     "Créer une salle",      "salle"),
            new Def(SALLE_MODIFIER,  "Modifier une salle",   "salle"),
            new Def(SALLE_SUPPRIMER, "Supprimer une salle",  "salle"),

            new Def(TABLE_CONSULTER,      "Consulter les tables",       "table"),
            new Def(TABLE_CREER,          "Créer une table",            "table"),
            new Def(TABLE_MODIFIER,       "Modifier une table",         "table"),
            new Def(TABLE_SUPPRIMER,      "Supprimer une table",        "table"),
            new Def(TABLE_CHANGER_STATUT, "Changer le statut d'une table", "table"),

            new Def(RESERVATION_CONSULTER, "Consulter les réservations", "reservation"),
            new Def(RESERVATION_CREER,     "Créer une réservation",      "reservation"),
            new Def(RESERVATION_MODIFIER,  "Modifier une réservation",   "reservation"),
            new Def(RESERVATION_SUPPRIMER, "Supprimer une réservation",  "reservation"),
            new Def(RESERVATION_CONFIRMER, "Confirmer une réservation",  "reservation"),
            new Def(RESERVATION_ANNULER,   "Annuler une réservation",    "reservation"),

            new Def(COMMANDE_CONSULTER, "Consulter les commandes", "commande"),
            new Def(COMMANDE_CREER,     "Créer une commande",      "commande"),
            new Def(COMMANDE_MODIFIER,  "Modifier une commande",   "commande"),
            new Def(COMMANDE_SUPPRIMER, "Supprimer une commande",  "commande"),
            new Def(COMMANDE_ENVOYER,   "Envoyer une commande en cuisine", "commande"),
            new Def(COMMANDE_ANNULER,   "Annuler une commande",    "commande"),

            new Def(CUISINE_CONSULTER,      "Consulter l'écran cuisine",        "cuisine"),
            new Def(CUISINE_CHANGER_STATUT, "Changer le statut de préparation", "cuisine"),

            new Def(SESSION_CAISSE_CONSULTER, "Consulter les sessions de caisse", "session_caisse"),
            new Def(SESSION_CAISSE_OUVRIR,    "Ouvrir une session de caisse",     "session_caisse"),
            new Def(SESSION_CAISSE_FERMER,    "Fermer une session de caisse",     "session_caisse"),

            new Def(PAIEMENT_CONSULTER, "Consulter les paiements", "paiement"),
            new Def(PAIEMENT_CREER,     "Encaisser un paiement",   "paiement"),
            new Def(PAIEMENT_ANNULER,   "Annuler un paiement",     "paiement"),

            new Def(ZONE_LIVRAISON_CONSULTER, "Consulter les zones de livraison", "zone_livraison"),
            new Def(ZONE_LIVRAISON_CREER,     "Créer une zone de livraison",      "zone_livraison"),
            new Def(ZONE_LIVRAISON_MODIFIER,  "Modifier une zone de livraison",   "zone_livraison"),
            new Def(ZONE_LIVRAISON_SUPPRIMER, "Supprimer une zone de livraison",  "zone_livraison"),

            new Def(LIVRAISON_CONSULTER,      "Consulter les livraisons",          "livraison"),
            new Def(LIVRAISON_CREER,          "Créer une livraison",               "livraison"),
            new Def(LIVRAISON_MODIFIER,       "Modifier une livraison",            "livraison"),
            new Def(LIVRAISON_CHANGER_STATUT, "Changer le statut d'une livraison", "livraison"),

            new Def(FOURNISSEUR_CONSULTER, "Consulter les fournisseurs", "fournisseur"),
            new Def(FOURNISSEUR_CREER,     "Créer un fournisseur",       "fournisseur"),
            new Def(FOURNISSEUR_MODIFIER,  "Modifier un fournisseur",    "fournisseur"),
            new Def(FOURNISSEUR_SUPPRIMER, "Supprimer un fournisseur",   "fournisseur"),

            new Def(COMMANDE_ACHAT_CONSULTER,    "Consulter les commandes d'achat",     "commande_achat"),
            new Def(COMMANDE_ACHAT_CREER,        "Créer une commande d'achat",          "commande_achat"),
            new Def(COMMANDE_ACHAT_MODIFIER,     "Modifier une commande d'achat",       "commande_achat"),
            new Def(COMMANDE_ACHAT_SUPPRIMER,    "Supprimer une commande d'achat",      "commande_achat"),
            new Def(COMMANDE_ACHAT_VALIDER,      "Valider une commande d'achat",        "commande_achat"),
            new Def(COMMANDE_ACHAT_RECEPTIONNER, "Réceptionner une commande d'achat",   "commande_achat"),
            new Def(COMMANDE_ACHAT_ANNULER,      "Annuler une commande d'achat",        "commande_achat"),

            new Def(PAIEMENT_ACHAT_CONSULTER, "Consulter les paiements fournisseur", "paiement_achat"),
            new Def(PAIEMENT_ACHAT_CREER,     "Payer un fournisseur",                "paiement_achat"),
            new Def(PAIEMENT_ACHAT_ANNULER,   "Annuler un paiement fournisseur",     "paiement_achat"),

            new Def(STOCK_CONSULTER,           "Consulter le stock",             "stock"),
            new Def(STOCK_MOUVEMENTER,         "Mouvementer le stock",           "stock"),
            new Def(MOUVEMENT_STOCK_CONSULTER, "Consulter les mouvements de stock", "mouvement_stock"),

            new Def(CLIENT_CONSULTER, "Consulter les clients", "client"),
            new Def(CLIENT_CREER,     "Créer un client",       "client"),
            new Def(CLIENT_MODIFIER,  "Modifier un client",    "client"),
            new Def(CLIENT_SUPPRIMER, "Supprimer un client",   "client"),

            new Def(PROMOTION_CONSULTER, "Consulter les promotions", "promotion"),
            new Def(PROMOTION_CREER,     "Créer une promotion",      "promotion"),
            new Def(PROMOTION_MODIFIER,  "Modifier une promotion",   "promotion"),
            new Def(PROMOTION_SUPPRIMER, "Supprimer une promotion",  "promotion"),

            new Def(FIDELITE_CONSULTER, "Consulter la fidélité",        "fidelite"),
            new Def(FIDELITE_GERER,     "Gérer la fidélité (points, bons)", "fidelite"),

            new Def(CATEGORIE_DEPENSE_CONSULTER, "Consulter les catégories de dépense", "categorie_depense"),
            new Def(CATEGORIE_DEPENSE_CREER,     "Créer une catégorie de dépense",      "categorie_depense"),
            new Def(CATEGORIE_DEPENSE_MODIFIER,  "Modifier une catégorie de dépense",   "categorie_depense"),
            new Def(CATEGORIE_DEPENSE_SUPPRIMER, "Supprimer une catégorie de dépense",  "categorie_depense"),

            new Def(DEPENSE_CONSULTER, "Consulter les dépenses", "depense"),
            new Def(DEPENSE_CREER,     "Créer une dépense",      "depense"),
            new Def(DEPENSE_MODIFIER,  "Modifier une dépense",   "depense"),
            new Def(DEPENSE_SUPPRIMER, "Supprimer une dépense",  "depense"),

            new Def(COMPTABILITE_CONSULTER, "Consulter la comptabilité", "comptabilite"),
            new Def(RAPPORT_CONSULTER,      "Consulter les rapports",    "rapport"),
            new Def(DASHBOARD_CONSULTER,    "Consulter le tableau de bord", "tableau_bord"),
            new Def(NOTIFICATION_CONSULTER, "Consulter les notifications", "notification"),

            new Def(MENU_TRAITEUR_CONSULTER, "Consulter les menus traiteur", "menu_traiteur"),
            new Def(MENU_TRAITEUR_CREER,     "Créer un menu traiteur",       "menu_traiteur"),
            new Def(MENU_TRAITEUR_MODIFIER,  "Modifier un menu traiteur",    "menu_traiteur"),
            new Def(MENU_TRAITEUR_SUPPRIMER, "Supprimer un menu traiteur",   "menu_traiteur"),

            new Def(PRESTATION_CONSULTER,      "Consulter les prestations",        "prestation"),
            new Def(PRESTATION_CREER,          "Créer une prestation",             "prestation"),
            new Def(PRESTATION_MODIFIER,       "Modifier une prestation",          "prestation"),
            new Def(PRESTATION_SUPPRIMER,      "Supprimer une prestation",         "prestation"),
            new Def(PRESTATION_CHANGER_STATUT, "Changer le statut d'une prestation", "prestation"),

            new Def(DEVIS_CONSULTER, "Consulter les devis", "devis"),
            new Def(DEVIS_CREER,     "Créer un devis",      "devis"),
            new Def(DEVIS_MODIFIER,  "Modifier un devis",   "devis"),
            new Def(DEVIS_SUPPRIMER, "Supprimer un devis",  "devis"),
            new Def(DEVIS_VALIDER,   "Valider un devis",    "devis"),
            new Def(DEVIS_CONVERTIR, "Convertir un devis en contrat", "devis"),

            new Def(CONTRAT_CONSULTER, "Consulter les contrats", "contrat"),
            new Def(CONTRAT_SIGNER,    "Signer un contrat",      "contrat"),
            new Def(CONTRAT_ANNULER,   "Annuler un contrat",     "contrat"),

            new Def(PAIEMENT_PRESTATION_CONSULTER, "Consulter les paiements de prestation", "paiement_prestation"),
            new Def(PAIEMENT_PRESTATION_CREER,     "Encaisser un acompte / solde",          "paiement_prestation"),
            new Def(PAIEMENT_PRESTATION_ANNULER,   "Annuler un paiement de prestation",     "paiement_prestation"),

            new Def(MATERIEL_LOCATION_CONSULTER, "Consulter les matériels de location", "materiel_location"),
            new Def(MATERIEL_LOCATION_CREER,     "Créer un matériel de location",       "materiel_location"),
            new Def(MATERIEL_LOCATION_MODIFIER,  "Modifier un matériel de location",    "materiel_location"),
            new Def(MATERIEL_LOCATION_SUPPRIMER, "Supprimer un matériel de location",   "materiel_location"),

            new Def(CONTRAT_LOCATION_CONSULTER, "Consulter les contrats de location", "contrat_location"),
            new Def(CONTRAT_LOCATION_CREER,     "Créer un contrat de location",       "contrat_location"),
            new Def(CONTRAT_LOCATION_MODIFIER,  "Modifier un contrat de location",    "contrat_location"),
            new Def(CONTRAT_LOCATION_RETOURNER, "Réceptionner un retour de location", "contrat_location"),
            new Def(CONTRAT_LOCATION_ANNULER,   "Annuler un contrat de location",     "contrat_location"),

            new Def(EVENEMENT_CONSULTER, "Consulter le calendrier des évènements", "evenement"),

            new Def(PERSONNEL_TRAITEUR_CONSULTER, "Consulter le personnel traiteur", "personnel_traiteur"),
            new Def(PERSONNEL_TRAITEUR_CREER,     "Créer un membre du personnel traiteur", "personnel_traiteur"),
            new Def(PERSONNEL_TRAITEUR_MODIFIER,  "Modifier un membre du personnel traiteur", "personnel_traiteur"),
            new Def(PERSONNEL_TRAITEUR_SUPPRIMER, "Supprimer un membre du personnel traiteur", "personnel_traiteur"),

            new Def(VEHICULE_CONSULTER, "Consulter les véhicules", "vehicule"),
            new Def(VEHICULE_CREER,     "Créer un véhicule",       "vehicule"),
            new Def(VEHICULE_MODIFIER,  "Modifier un véhicule",    "vehicule"),
            new Def(VEHICULE_SUPPRIMER, "Supprimer un véhicule",   "vehicule"),

            new Def(MATERIEL_TRAITEUR_CONSULTER, "Consulter le matériel traiteur", "materiel_traiteur"),
            new Def(MATERIEL_TRAITEUR_CREER,     "Créer du matériel traiteur",     "materiel_traiteur"),
            new Def(MATERIEL_TRAITEUR_MODIFIER,  "Modifier du matériel traiteur",  "materiel_traiteur"),
            new Def(MATERIEL_TRAITEUR_SUPPRIMER, "Supprimer du matériel traiteur", "materiel_traiteur"),

            new Def(AFFECTATION_CONSULTER, "Consulter les affectations", "affectation"),
            new Def(AFFECTATION_GERER,     "Gérer les affectations",     "affectation"),

            new Def(RAPPORT_TRAITEUR_CONSULTER, "Consulter les rapports traiteur", "rapport_traiteur")
    );
}
