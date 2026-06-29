package yamrestor.restor.service.impl.approvisionnement;

import yamrestor.restor.dto.request.approvisionnement.PaiementAchatRequest;
import yamrestor.restor.entity.approvisionnement.CommandeAchatEntity;
import yamrestor.restor.entity.approvisionnement.FournisseurEntity;
import yamrestor.restor.entity.approvisionnement.PaiementAchatEntity;
import yamrestor.restor.enums.StatutPaiement;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.approvisionnement.CommandeAchatRepository;
import yamrestor.restor.repository.approvisionnement.FournisseurRepository;
import yamrestor.restor.repository.approvisionnement.PaiementAchatRepository;
import yamrestor.restor.service.approvisionnement.PaiementAchatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaiementAchatServiceImpl implements PaiementAchatService {

    private final PaiementAchatRepository paiementAchatRepository;
    private final FournisseurRepository fournisseurRepository;
    private final CommandeAchatRepository commandeAchatRepository;

    @Override
    public Page<PaiementAchatEntity> search(StatutPaiement statut, int page, int size) {
        return paiementAchatRepository.search(statut, PageRequest.of(page, size));
    }

    @Override
    public PaiementAchatEntity findByGuid(String guid) {
        return paiementAchatRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("PaiementAchatEntity", guid));
    }

    @Override
    @Transactional
    public PaiementAchatEntity creer(PaiementAchatRequest req) {
        FournisseurEntity f = fournisseurRepository.findByGuid(req.getFournisseurGuid())
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", req.getFournisseurGuid()));

        PaiementAchatEntity p = new PaiementAchatEntity();
        p.setFournisseur(f);
        p.setCommandeAchat(resolveCommande(req.getCommandeAchatGuid()));
        p.setMontant(req.getMontant());
        p.setMode(req.getMode());
        p.setReference(req.getReference());
        p.setStatut(StatutPaiement.VALIDE);

        // Le paiement diminue le solde dû au fournisseur.
        BigDecimal solde = f.getSolde() != null ? f.getSolde() : BigDecimal.ZERO;
        f.setSolde(solde.subtract(req.getMontant()));
        fournisseurRepository.save(f);

        PaiementAchatEntity saved = paiementAchatRepository.save(p);
        if (saved.getNumero() == null) {
            saved.setNumero("PA-" + String.format("%05d", saved.getId()));
            saved = paiementAchatRepository.save(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public PaiementAchatEntity annuler(String guid) {
        PaiementAchatEntity p = findByGuid(guid);
        if (p.getStatut() == StatutPaiement.ANNULE) {
            throw new BadRequestException("Ce paiement est déjà annulé.");
        }
        p.setStatut(StatutPaiement.ANNULE);

        FournisseurEntity f = p.getFournisseur();
        if (f != null) {
            BigDecimal solde = f.getSolde() != null ? f.getSolde() : BigDecimal.ZERO;
            f.setSolde(solde.add(p.getMontant() != null ? p.getMontant() : BigDecimal.ZERO));
            fournisseurRepository.save(f);
        }
        return paiementAchatRepository.save(p);
    }

    private CommandeAchatEntity resolveCommande(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return commandeAchatRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("CommandeAchat", guid));
    }
}
