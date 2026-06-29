package yamrestor.restor.service.stock;

import yamrestor.restor.entity.catalogue.MatierePremiereEntity;
import yamrestor.restor.entity.stock.MouvementStockEntity;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface StockService {
    /** Entrée de stock (réception, entrée directe). */
    MouvementStockEntity entree(String matiereGuid, BigDecimal quantite, String motif, String reference);
    /** Sortie de stock (sortie directe, perte). */
    MouvementStockEntity sortie(String matiereGuid, BigDecimal quantite, String motif, String reference);
    /** Ajustement d'inventaire : fixe le stock à la valeur comptée (mouvement signé). */
    MouvementStockEntity ajuster(String matiereGuid, BigDecimal nouveauStock, String motif);
    /** Consommation automatique (recette) — overload entité pour usage interne. */
    MouvementStockEntity consommer(MatierePremiereEntity matiere, BigDecimal quantite, String motif, String reference);

    Page<MouvementStockEntity> mouvements(String matiereGuid, int page, int size);
    /** Matières premières dont le stock est sous le seuil d'alerte. */
    List<MatierePremiereEntity> alertes();
    /** Valorisation totale du stock = somme(stock × prix d'achat). */
    BigDecimal valorisation();
}
