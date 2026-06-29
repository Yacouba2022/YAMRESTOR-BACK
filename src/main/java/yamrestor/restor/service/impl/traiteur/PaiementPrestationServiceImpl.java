package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.dto.request.traiteur.PaiementPrestationRequest;
import yamrestor.restor.entity.traiteur.PaiementPrestationEntity;
import yamrestor.restor.entity.traiteur.PrestationEntity;
import yamrestor.restor.enums.StatutPaiement;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.traiteur.PaiementPrestationRepository;
import yamrestor.restor.repository.traiteur.PrestationRepository;
import yamrestor.restor.service.traiteur.PaiementPrestationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementPrestationServiceImpl implements PaiementPrestationService {

    private final PaiementPrestationRepository paiementRepository;
    private final PrestationRepository prestationRepository;

    @Override
    public PaiementPrestationEntity findByGuid(String guid) {
        return paiementRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("PaiementPrestationEntity", guid));
    }

    @Override
    public List<PaiementPrestationEntity> paiementsPrestation(String prestationGuid) {
        return paiementRepository.findByPrestationGuidOrderByCreatedAtDesc(prestationGuid);
    }

    @Override
    @Transactional
    public PaiementPrestationEntity creer(PaiementPrestationRequest req) {
        PrestationEntity prestation = prestationRepository.findByGuid(req.getPrestationGuid())
                .orElseThrow(() -> new ResourceNotFoundException("Prestation", req.getPrestationGuid()));

        PaiementPrestationEntity p = new PaiementPrestationEntity();
        p.setPrestation(prestation);
        p.setMontant(req.getMontant());
        p.setMode(req.getMode());
        p.setType(req.getType());
        p.setReference(req.getReference());
        p.setStatut(StatutPaiement.VALIDE);

        BigDecimal paye = prestation.getMontantPaye() != null ? prestation.getMontantPaye() : BigDecimal.ZERO;
        prestation.setMontantPaye(paye.add(req.getMontant()));
        prestationRepository.save(prestation);

        PaiementPrestationEntity saved = paiementRepository.save(p);
        if (saved.getNumero() == null) {
            saved.setNumero("PP-" + String.format("%05d", saved.getId()));
            saved = paiementRepository.save(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public PaiementPrestationEntity annuler(String guid) {
        PaiementPrestationEntity p = findByGuid(guid);
        if (p.getStatut() == StatutPaiement.ANNULE) {
            throw new BadRequestException("Ce paiement est déjà annulé.");
        }
        p.setStatut(StatutPaiement.ANNULE);

        PrestationEntity prestation = p.getPrestation();
        if (prestation != null) {
            BigDecimal paye = prestation.getMontantPaye() != null ? prestation.getMontantPaye() : BigDecimal.ZERO;
            prestation.setMontantPaye(paye.subtract(p.getMontant() != null ? p.getMontant() : BigDecimal.ZERO));
            prestationRepository.save(prestation);
        }
        return paiementRepository.save(p);
    }
}
