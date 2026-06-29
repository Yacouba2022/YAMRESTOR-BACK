package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.dto.request.traiteur.DevisRequest;
import yamrestor.restor.entity.traiteur.*;
import yamrestor.restor.enums.StatutContrat;
import yamrestor.restor.enums.StatutDevis;
import yamrestor.restor.enums.StatutPrestation;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.traiteur.ContratRepository;
import yamrestor.restor.repository.traiteur.DevisRepository;
import yamrestor.restor.repository.traiteur.MenuTraiteurRepository;
import yamrestor.restor.repository.traiteur.PrestationRepository;
import yamrestor.restor.service.traiteur.DevisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DevisServiceImpl implements DevisService {

    private final DevisRepository devisRepository;
    private final PrestationRepository prestationRepository;
    private final MenuTraiteurRepository menuRepository;
    private final ContratRepository contratRepository;

    @Override
    public Page<DevisEntity> search(StatutDevis statut, int page, int size) {
        return devisRepository.search(statut, PageRequest.of(page, size));
    }

    @Override
    public DevisEntity findByGuid(String guid) {
        return devisRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("DevisEntity", guid));
    }

    @Override
    @Transactional
    public DevisEntity creerDepuisRequest(DevisRequest req) {
        DevisEntity d = new DevisEntity();
        d.setPrestation(resolvePrestation(req.getPrestationGuid()));
        d.setObservations(req.getObservations());
        d.setStatut(StatutDevis.BROUILLON);
        remplacerLignes(d, req);
        recalculerTotal(d);

        DevisEntity saved = devisRepository.save(d);
        if (saved.getNumero() == null) {
            saved.setNumero("DV-" + String.format("%05d", saved.getId()));
            saved = devisRepository.save(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public DevisEntity modifierDepuisRequest(String guid, DevisRequest req) {
        DevisEntity d = findByGuid(guid);
        if (d.getStatut() != StatutDevis.BROUILLON) {
            throw new BadRequestException("Seul un devis en brouillon peut être modifié.");
        }
        d.setObservations(req.getObservations());
        remplacerLignes(d, req);
        recalculerTotal(d);
        return devisRepository.save(d);
    }

    @Override
    @Transactional
    public DevisEntity valider(String guid) {
        DevisEntity d = findByGuid(guid);
        if (d.getStatut() != StatutDevis.BROUILLON) {
            throw new BadRequestException("Seul un devis en brouillon peut être validé.");
        }
        if (d.getLignes().isEmpty()) {
            throw new BadRequestException("Impossible de valider un devis vide.");
        }
        d.setStatut(StatutDevis.VALIDE);
        return devisRepository.save(d);
    }

    @Override
    @Transactional
    public ContratEntity convertirEnContrat(String guid) {
        DevisEntity d = findByGuid(guid);
        if (d.getStatut() != StatutDevis.VALIDE) {
            throw new BadRequestException("Seul un devis validé peut être converti en contrat.");
        }

        ContratEntity contrat = new ContratEntity();
        contrat.setPrestation(d.getPrestation());
        contrat.setDevis(d);
        contrat.setMontant(d.getMontantTotal());
        contrat.setStatut(StatutContrat.BROUILLON);
        ContratEntity savedContrat = contratRepository.save(contrat);
        if (savedContrat.getNumero() == null) {
            savedContrat.setNumero("CTR-" + String.format("%05d", savedContrat.getId()));
            savedContrat = contratRepository.save(savedContrat);
        }

        d.setStatut(StatutDevis.CONVERTI);
        devisRepository.save(d);

        // Confirme la prestation et fixe son montant facturé.
        PrestationEntity prestation = d.getPrestation();
        if (prestation != null) {
            prestation.setMontantTotal(d.getMontantTotal());
            prestation.setStatut(StatutPrestation.CONFIRMEE);
            prestationRepository.save(prestation);
        }
        return savedContrat;
    }

    @Override
    @Transactional
    public void delete(String guid) {
        DevisEntity d = findByGuid(guid);
        if (d.getStatut() == StatutDevis.CONVERTI) {
            throw new BadRequestException("Un devis converti en contrat ne peut pas être supprimé.");
        }
        devisRepository.delete(d);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────────

    private void remplacerLignes(DevisEntity d, DevisRequest req) {
        d.getLignes().clear();
        if (req.getLignes() == null) return;
        for (DevisRequest.LigneRequest lr : req.getLignes()) {
            DevisLigneEntity ligne = new DevisLigneEntity();
            ligne.setDevis(d);
            ligne.setDesignation(lr.getDesignation());
            ligne.setMenuTraiteur(resolveMenu(lr.getMenuTraiteurGuid()));
            ligne.setQuantite(lr.getQuantite());
            ligne.setPrixUnitaire(lr.getPrixUnitaire());
            ligne.setMontantLigne(lr.getPrixUnitaire().multiply(lr.getQuantite()));
            d.getLignes().add(ligne);
        }
    }

    private void recalculerTotal(DevisEntity d) {
        BigDecimal total = d.getLignes().stream()
                .map(DevisLigneEntity::getMontantLigne)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        d.setMontantTotal(total);
    }

    private PrestationEntity resolvePrestation(String guid) {
        return prestationRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Prestation", guid));
    }

    private MenuTraiteurEntity resolveMenu(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return menuRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("MenuTraiteur", guid));
    }
}
