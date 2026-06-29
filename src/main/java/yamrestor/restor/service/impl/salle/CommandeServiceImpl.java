package yamrestor.restor.service.impl.salle;

import yamrestor.restor.dto.request.salle.CommandeRequest;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.entity.catalogue.ProduitEntity;
import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.entity.salle.CommandeLigneEntity;
import yamrestor.restor.entity.salle.TableEntity;
import yamrestor.restor.enums.*;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.entity.catalogue.RecetteEntity;
import yamrestor.restor.entity.catalogue.RecetteLigneEntity;
import yamrestor.restor.repository.administration.UserRepository;
import yamrestor.restor.repository.catalogue.ProduitRepository;
import yamrestor.restor.repository.catalogue.RecetteRepository;
import yamrestor.restor.repository.salle.CommandeRepository;
import yamrestor.restor.repository.salle.TableRepository;
import yamrestor.restor.service.salle.CommandeService;
import yamrestor.restor.service.stock.StockService;
import yamrestor.restor.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProduitRepository produitRepository;
    private final TableRepository tableRepository;
    private final UserRepository userRepository;
    private final RecetteRepository recetteRepository;
    private final StockService stockService;

    @Override
    public Page<CommandeEntity> search(StatutCommande statut, TypeCommande type, int page, int size) {
        return commandeRepository.search(statut, type, PageRequest.of(page, size));
    }

    @Override
    public CommandeEntity findByGuid(String guid) {
        return commandeRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("CommandeEntity", guid));
    }

    @Override
    @Transactional
    public CommandeEntity creerDepuisRequest(CommandeRequest req) {
        CommandeEntity c = new CommandeEntity();
        c.setType(req.getType());
        c.setTable(resolveTable(req.getTableGuid()));
        c.setServeur(resolveServeur(req.getServeurGuid()));
        c.setClientNom(req.getClientNom());
        c.setClientTelephone(req.getClientTelephone());
        c.setPriorite(req.getPriorite() != null ? req.getPriorite() : Priorite.NORMALE);
        c.setObservations(req.getObservations());
        c.setStatut(StatutCommande.EN_COURS);
        remplacerLignes(c, req);
        recalculerTotal(c);

        CommandeEntity saved = commandeRepository.save(c);
        if (saved.getNumero() == null) {
            saved.setNumero(Constants.PREFIX_COMMANDE + "-" + String.format("%05d", saved.getId()));
            saved = commandeRepository.save(saved);
        }

        // Table occupée pour une commande sur place.
        if (saved.getType() == TypeCommande.SUR_PLACE && saved.getTable() != null) {
            saved.getTable().setStatut(StatutTable.OCCUPEE);
            tableRepository.save(saved.getTable());
        }
        return saved;
    }

    @Override
    @Transactional
    public CommandeEntity modifierDepuisRequest(String guid, CommandeRequest req) {
        CommandeEntity c = findByGuid(guid);
        if (c.getStatut() != StatutCommande.EN_COURS) {
            throw new BadRequestException("Seule une commande en cours peut être modifiée.");
        }
        c.setTable(resolveTable(req.getTableGuid()));
        c.setServeur(resolveServeur(req.getServeurGuid()));
        c.setClientNom(req.getClientNom());
        c.setClientTelephone(req.getClientTelephone());
        if (req.getPriorite() != null) c.setPriorite(req.getPriorite());
        c.setObservations(req.getObservations());
        remplacerLignes(c, req);
        recalculerTotal(c);
        return commandeRepository.save(c);
    }

    @Override
    @Transactional
    public CommandeEntity envoyer(String guid) {
        CommandeEntity c = findByGuid(guid);
        if (c.getStatut() != StatutCommande.EN_COURS) {
            throw new BadRequestException("Seule une commande en cours peut être envoyée en cuisine.");
        }
        if (c.getLignes().isEmpty()) {
            throw new BadRequestException("Impossible d'envoyer une commande vide.");
        }
        consommerMatieresPremieres(c);
        c.setStatut(StatutCommande.ENVOYEE);
        return commandeRepository.save(c);
    }

    /**
     * Décrémente automatiquement les matières premières selon la recette de chaque produit
     * commandé (quantité recette × quantité commandée). Les produits sans recette sont ignorés.
     */
    private void consommerMatieresPremieres(CommandeEntity c) {
        for (CommandeLigneEntity ligne : c.getLignes()) {
            if (ligne.getProduit() == null) continue;
            RecetteEntity recette = recetteRepository.findByProduitGuid(ligne.getProduit().getGuid()).orElse(null);
            if (recette == null) continue;
            int qteCommande = ligne.getQuantite() != null ? ligne.getQuantite() : 1;
            for (RecetteLigneEntity rl : recette.getLignes()) {
                if (rl.getMatierePremiere() == null || rl.getQuantite() == null) continue;
                BigDecimal aConsommer = rl.getQuantite().multiply(BigDecimal.valueOf(qteCommande));
                stockService.consommer(rl.getMatierePremiere(), aConsommer,
                        "Commande " + c.getNumero(), c.getNumero());
            }
        }
    }

    @Override
    @Transactional
    public CommandeEntity annuler(String guid) {
        CommandeEntity c = findByGuid(guid);
        c.setStatut(StatutCommande.ANNULEE);
        libererTable(c);
        return commandeRepository.save(c);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        CommandeEntity c = findByGuid(guid);
        libererTable(c);
        commandeRepository.delete(c);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────────

    private void remplacerLignes(CommandeEntity c, CommandeRequest req) {
        c.getLignes().clear();
        if (req.getLignes() == null) return;
        for (CommandeRequest.LigneRequest lr : req.getLignes()) {
            ProduitEntity produit = produitRepository.findByGuid(lr.getProduitGuid())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit", lr.getProduitGuid()));
            BigDecimal prix = produit.getPrixVente() != null ? produit.getPrixVente() : BigDecimal.ZERO;
            int qte = lr.getQuantite() != null ? lr.getQuantite() : 1;

            CommandeLigneEntity ligne = new CommandeLigneEntity();
            ligne.setCommande(c);
            ligne.setProduit(produit);
            ligne.setProduitNom(produit.getNom());
            ligne.setQuantite(qte);
            ligne.setPrixUnitaire(prix);
            ligne.setMontantLigne(prix.multiply(BigDecimal.valueOf(qte)));
            ligne.setNotes(lr.getNotes());
            ligne.setStatutPreparation(StatutPreparation.EN_ATTENTE);
            c.getLignes().add(ligne);
        }
    }

    private void recalculerTotal(CommandeEntity c) {
        BigDecimal total = c.getLignes().stream()
                .map(CommandeLigneEntity::getMontantLigne)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        c.setMontantTotal(total);
    }

    private void libererTable(CommandeEntity c) {
        TableEntity t = c.getTable();
        if (t != null && (t.getStatut() == StatutTable.OCCUPEE || t.getStatut() == StatutTable.RESERVEE)) {
            t.setStatut(StatutTable.LIBRE);
            tableRepository.save(t);
        }
    }

    private TableEntity resolveTable(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return tableRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Table", guid));
    }

    private UserEntity resolveServeur(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return userRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Serveur", guid));
    }
}
