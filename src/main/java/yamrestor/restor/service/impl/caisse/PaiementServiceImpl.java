package yamrestor.restor.service.impl.caisse;

import yamrestor.restor.dto.caisse.PaiementSituationDTO;
import yamrestor.restor.dto.request.caisse.PaiementRequest;
import yamrestor.restor.entity.caisse.PaiementEntity;
import yamrestor.restor.entity.caisse.PaiementLigneEntity;
import yamrestor.restor.entity.caisse.SessionCaisseEntity;
import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.enums.StatutPaiement;
import yamrestor.restor.enums.StatutSessionCaisse;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.mapper.caisse.PaiementMapper;
import yamrestor.restor.repository.caisse.PaiementRepository;
import yamrestor.restor.repository.caisse.SessionCaisseRepository;
import yamrestor.restor.repository.salle.CommandeRepository;
import yamrestor.restor.service.caisse.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final CommandeRepository commandeRepository;
    private final SessionCaisseRepository sessionRepository;

    @Override
    public Page<PaiementEntity> search(StatutPaiement statut, int page, int size) {
        return paiementRepository.search(statut, PageRequest.of(page, size));
    }

    @Override
    public PaiementEntity findByGuid(String guid) {
        return paiementRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("PaiementEntity", guid));
    }

    @Override
    @Transactional
    public PaiementEntity creer(PaiementRequest req, String currentUserGuid) {
        CommandeEntity commande = commandeRepository.findByGuid(req.getCommandeGuid())
                .orElseThrow(() -> new ResourceNotFoundException("Commande", req.getCommandeGuid()));

        SessionCaisseEntity session = resolveSession(req.getSessionCaisseGuid(), currentUserGuid);

        PaiementEntity p = new PaiementEntity();
        p.setCommande(commande);
        p.setSessionCaisse(session);
        p.setRendu(req.getRendu() != null ? req.getRendu() : BigDecimal.ZERO);
        p.setStatut(StatutPaiement.VALIDE);

        BigDecimal montant = BigDecimal.ZERO;
        for (PaiementRequest.LigneRequest lr : req.getLignes()) {
            PaiementLigneEntity ligne = new PaiementLigneEntity();
            ligne.setPaiement(p);
            ligne.setMode(lr.getMode());
            ligne.setMontant(lr.getMontant());
            ligne.setReference(lr.getReference());
            p.getLignes().add(ligne);
            montant = montant.add(lr.getMontant());
        }
        p.setMontant(montant);

        PaiementEntity saved = paiementRepository.save(p);
        if (saved.getNumero() == null) {
            saved.setNumero("PAI-" + String.format("%05d", saved.getId()));
            saved = paiementRepository.save(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public PaiementEntity annuler(String guid) {
        PaiementEntity p = findByGuid(guid);
        p.setStatut(StatutPaiement.ANNULE);
        return paiementRepository.save(p);
    }

    @Override
    @Transactional(readOnly = true)
    public PaiementSituationDTO situationCommande(String commandeGuid) {
        CommandeEntity commande = commandeRepository.findByGuid(commandeGuid)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", commandeGuid));

        List<PaiementEntity> paiements =
                paiementRepository.findByCommandeGuidAndStatut(commandeGuid, StatutPaiement.VALIDE);

        BigDecimal montantCommande = commande.getMontantTotal() != null ? commande.getMontantTotal() : BigDecimal.ZERO;
        BigDecimal totalPaye = paiements.stream()
                .map(p -> p.getMontant() != null ? p.getMontant() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal reste = montantCommande.subtract(totalPaye);

        return PaiementSituationDTO.builder()
                .commandeGuid(commande.getGuid())
                .commandeNumero(commande.getNumero())
                .montantCommande(montantCommande)
                .totalPaye(totalPaye)
                .resteAPayer(reste.max(BigDecimal.ZERO))
                .soldee(reste.compareTo(BigDecimal.ZERO) <= 0)
                .paiements(paiements.stream().map(PaiementMapper::toDTO).toList())
                .build();
    }

    private SessionCaisseEntity resolveSession(String sessionGuid, String currentUserGuid) {
        if (sessionGuid != null && !sessionGuid.isBlank()) {
            return sessionRepository.findByGuid(sessionGuid)
                    .orElseThrow(() -> new ResourceNotFoundException("SessionCaisse", sessionGuid));
        }
        if (currentUserGuid != null) {
            return sessionRepository
                    .findFirstByCaissierGuidAndStatut(currentUserGuid, StatutSessionCaisse.OUVERTE)
                    .orElse(null);
        }
        return null;
    }
}
