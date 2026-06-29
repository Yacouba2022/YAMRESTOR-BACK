package yamrestor.restor.enums;

/** Cycle de vie d'une commande (le paiement est géré par le module Caisse — Phase 4). */
public enum StatutCommande {
    EN_COURS,   // en cours de saisie
    ENVOYEE,    // envoyée en cuisine/bar
    TERMINEE,   // tous les plats servis
    ANNULEE
}
