package yamrestor.restor.service.impl.approvisionnement;

import yamrestor.restor.dto.request.approvisionnement.CommandeAchatRequest;
import yamrestor.restor.entity.approvisionnement.CommandeAchatEntity;
import yamrestor.restor.entity.approvisionnement.CommandeAchatLigneEntity;
import yamrestor.restor.entity.approvisionnement.FournisseurEntity;
import yamrestor.restor.entity.catalogue.MatierePremiereEntity;
import yamrestor.restor.enums.StatutCommandeAchat;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.approvisionnement.CommandeAchatRepository;
import yamrestor.restor.repository.approvisionnement.FournisseurRepository;
import yamrestor.restor.repository.catalogue.MatierePremiereRepository;
import yamrestor.restor.service.approvisionnement.CommandeAchatService;
import yamrestor.restor.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommandeAchatServiceImpl implements CommandeAchatService {

    private final CommandeAchatRepository commandeAchatRepository;
    private final FournisseurRepository fournisseurRepository;
    private final MatierePremiereRepository matiereRepository;
    private final StockService stockService;

    @Override
    public Page<CommandeAchatEntity> search(StatutCommandeAchat statut, int page, int size) {
        return commandeAchatRepository.search(statut, PageRequest.of(page, size));
    }

    @Override
    public CommandeAchatEntity findByGuid(String guid) {
        return commandeAchatRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("CommandeAchatEntity", guid));
    }

    @Override
    @Transactional
    public CommandeAchatEntity creerDepuisRequest(CommandeAchatRequest req) {
        CommandeAchatEntity c = new CommandeAchatEntity();
        c.setFournisseur(resolveFournisseur(req.getFournisseurGuid()));
        c.setObservations(req.getObservations());
        c.setStatut(StatutCommandeAchat.BROUILLON);
        remplacerLignes(c, req);
        recalculerTotal(c);

        CommandeAchatEntity saved = commandeAchatRepository.save(c);
        if (saved.getNumero() == null) {
            saved.setNumero("CA-" + String.format("%05d", saved.getId()));
            saved = commandeAchatRepository.save(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public CommandeAchatEntity modifierDepuisRequest(String guid, CommandeAchatRequest req) {
        CommandeAchatEntity c = findByGuid(guid);
        if (c.getStatut() != StatutCommandeAchat.BROUILLON) {
            throw new BadRequestException("Seule une commande d'achat en brouillon peut être modifiée.");
        }
        c.setFournisseur(resolveFournisseur(req.getFournisseurGuid()));
        c.setObservations(req.getObservations());
        remplacerLignes(c, req);
        recalculerTotal(c);
        return commandeAchatRepository.save(c);
    }

    @Override
    @Transactional
    public CommandeAchatEntity valider(String guid) {
        CommandeAchatEntity c = findByGuid(guid);
        if (c.getStatut() != StatutCommandeAchat.BROUILLON) {
            throw new BadRequestException("Seule une commande en brouillon peut être validée.");
        }
        if (c.getLignes().isEmpty()) {
            throw new BadRequestException("Impossible de valider une commande d'achat vide.");
        }
        c.setStatut(StatutCommandeAchat.VALIDEE);
        return commandeAchatRepository.save(c);
    }

    @Override
    @Transactional
    public CommandeAchatEntity receptionner(String guid) {
        CommandeAchatEntity c = findByGuid(guid);
        if (c.getStatut() == StatutCommandeAchat.RECEPTIONNEE) {
            throw new BadRequestException("Cette commande a déjà été réceptionnée.");
        }
        if (c.getStatut() == StatutCommandeAchat.ANNULEE) {
            throw new BadRequestException("Une commande annulée ne peut pas être réceptionnée.");
        }

        // Entrées de stock pour chaque ligne
        for (CommandeAchatLigneEntity l : c.getLignes()) {
            if (l.getMatierePremiere() != null && l.getQuantite() != null) {
                stockService.entree(l.getMatierePremiere().getGuid(), l.getQuantite(),
                        "Réception " + c.getNumero(), c.getNumero());
            }
        }

        // Le restaurant doit désormais le montant au fournisseur.
        FournisseurEntity f = c.getFournisseur();
        if (f != null) {
            BigDecimal solde = f.getSolde() != null ? f.getSolde() : BigDecimal.ZERO;
            f.setSolde(solde.add(c.getMontantTotal() != null ? c.getMontantTotal() : BigDecimal.ZERO));
            fournisseurRepository.save(f);
        }

        c.setStatut(StatutCommandeAchat.RECEPTIONNEE);
        c.setDateReception(LocalDate.now());
        return commandeAchatRepository.save(c);
    }

    @Override
    @Transactional
    public CommandeAchatEntity annuler(String guid) {
        CommandeAchatEntity c = findByGuid(guid);
        if (c.getStatut() == StatutCommandeAchat.RECEPTIONNEE) {
            throw new BadRequestException("Une commande réceptionnée ne peut pas être annulée.");
        }
        c.setStatut(StatutCommandeAchat.ANNULEE);
        return commandeAchatRepository.save(c);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        CommandeAchatEntity c = findByGuid(guid);
        if (c.getStatut() == StatutCommandeAchat.RECEPTIONNEE) {
            throw new BadRequestException("Une commande réceptionnée ne peut pas être supprimée.");
        }
        commandeAchatRepository.delete(c);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────────

    private void remplacerLignes(CommandeAchatEntity c, CommandeAchatRequest req) {
        c.getLignes().clear();
        if (req.getLignes() == null) return;
        for (CommandeAchatRequest.LigneRequest lr : req.getLignes()) {
            MatierePremiereEntity matiere = matiereRepository.findByGuid(lr.getMatierePremiereGuid())
                    .orElseThrow(() -> new ResourceNotFoundException("MatierePremiere", lr.getMatierePremiereGuid()));
            BigDecimal prix = lr.getPrixUnitaire() != null ? lr.getPrixUnitaire()
                    : (matiere.getPrixAchat() != null ? matiere.getPrixAchat() : BigDecimal.ZERO);

            CommandeAchatLigneEntity ligne = new CommandeAchatLigneEntity();
            ligne.setCommandeAchat(c);
            ligne.setMatierePremiere(matiere);
            ligne.setQuantite(lr.getQuantite());
            ligne.setPrixUnitaire(prix);
            ligne.setMontantLigne(prix.multiply(lr.getQuantite()));
            c.getLignes().add(ligne);
        }
    }

    private void recalculerTotal(CommandeAchatEntity c) {
        BigDecimal total = c.getLignes().stream()
                .map(CommandeAchatLigneEntity::getMontantLigne)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        c.setMontantTotal(total);
    }

    private FournisseurEntity resolveFournisseur(String guid) {
        return fournisseurRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", guid));
    }
}
