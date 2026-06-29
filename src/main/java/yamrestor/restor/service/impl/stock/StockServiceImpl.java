package yamrestor.restor.service.impl.stock;

import yamrestor.restor.entity.catalogue.MatierePremiereEntity;
import yamrestor.restor.entity.stock.MouvementStockEntity;
import yamrestor.restor.enums.TypeMouvement;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.catalogue.MatierePremiereRepository;
import yamrestor.restor.repository.stock.MouvementStockRepository;
import yamrestor.restor.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final MouvementStockRepository mouvementRepository;
    private final MatierePremiereRepository matiereRepository;

    @Override
    @Transactional
    public MouvementStockEntity entree(String matiereGuid, BigDecimal quantite, String motif, String reference) {
        return appliquer(resolve(matiereGuid), TypeMouvement.ENTREE, quantite.abs(), motif, reference);
    }

    @Override
    @Transactional
    public MouvementStockEntity sortie(String matiereGuid, BigDecimal quantite, String motif, String reference) {
        return appliquer(resolve(matiereGuid), TypeMouvement.SORTIE, quantite.abs().negate(), motif, reference);
    }

    @Override
    @Transactional
    public MouvementStockEntity ajuster(String matiereGuid, BigDecimal nouveauStock, String motif) {
        MatierePremiereEntity m = resolve(matiereGuid);
        BigDecimal actuel = m.getStock() != null ? m.getStock() : BigDecimal.ZERO;
        BigDecimal delta = nouveauStock.subtract(actuel);
        return appliquer(m, TypeMouvement.AJUSTEMENT, delta, motif, null);
    }

    @Override
    @Transactional
    public MouvementStockEntity consommer(MatierePremiereEntity matiere, BigDecimal quantite, String motif, String reference) {
        return appliquer(matiere, TypeMouvement.CONSOMMATION, quantite.abs().negate(), motif, reference);
    }

    @Override
    public Page<MouvementStockEntity> mouvements(String matiereGuid, int page, int size) {
        String guid = (matiereGuid != null && !matiereGuid.isBlank()) ? matiereGuid : null;
        return mouvementRepository.search(guid, PageRequest.of(page, size));
    }

    @Override
    public List<MatierePremiereEntity> alertes() {
        return matiereRepository.findEnAlerte();
    }

    @Override
    public BigDecimal valorisation() {
        BigDecimal v = matiereRepository.valorisationTotale();
        return v != null ? v : BigDecimal.ZERO;
    }

    /** Applique une variation signée au stock d'une matière et journalise le mouvement. */
    private MouvementStockEntity appliquer(MatierePremiereEntity matiere, TypeMouvement type,
                                           BigDecimal delta, String motif, String reference) {
        BigDecimal avant = matiere.getStock() != null ? matiere.getStock() : BigDecimal.ZERO;
        BigDecimal apres = avant.add(delta);
        matiere.setStock(apres);
        matiereRepository.save(matiere);

        MouvementStockEntity mvt = new MouvementStockEntity();
        mvt.setMatierePremiere(matiere);
        mvt.setType(type);
        mvt.setQuantite(delta);
        mvt.setStockAvant(avant);
        mvt.setStockApres(apres);
        mvt.setMotif(motif);
        mvt.setReference(reference);
        return mouvementRepository.save(mvt);
    }

    private MatierePremiereEntity resolve(String guid) {
        return matiereRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("MatierePremiere", guid));
    }
}
