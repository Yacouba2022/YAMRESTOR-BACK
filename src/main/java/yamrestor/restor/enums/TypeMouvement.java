package yamrestor.restor.enums;

/** Sens / nature d'un mouvement de stock. */
public enum TypeMouvement {
    ENTREE,        // réception, entrée directe
    SORTIE,        // sortie directe, perte
    AJUSTEMENT,    // correction d'inventaire (signé)
    CONSOMMATION   // décrément automatique lié à une recette
}
