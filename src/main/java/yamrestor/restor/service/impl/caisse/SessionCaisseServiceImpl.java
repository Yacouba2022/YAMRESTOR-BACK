package yamrestor.restor.service.impl.caisse;

import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.entity.caisse.PaiementEntity;
import yamrestor.restor.entity.caisse.PaiementLigneEntity;
import yamrestor.restor.entity.caisse.SessionCaisseEntity;
import yamrestor.restor.enums.ModePaiement;
import yamrestor.restor.enums.StatutPaiement;
import yamrestor.restor.enums.StatutSessionCaisse;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.administration.UserRepository;
import yamrestor.restor.repository.caisse.PaiementRepository;
import yamrestor.restor.repository.caisse.SessionCaisseRepository;
import yamrestor.restor.service.caisse.SessionCaisseService;
import yamrestor.restor.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionCaisseServiceImpl implements SessionCaisseService {

    private final SessionCaisseRepository sessionRepository;
    private final PaiementRepository paiementRepository;
    private final UserRepository userRepository;

    @Override
    public Page<SessionCaisseEntity> search(StatutSessionCaisse statut, int page, int size) {
        return sessionRepository.search(statut, PageRequest.of(page, size));
    }

    @Override
    public SessionCaisseEntity findByGuid(String guid) {
        return sessionRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("SessionCaisseEntity", guid));
    }

    @Override
    public SessionCaisseEntity sessionOuverte(String caissierGuid) {
        return sessionRepository
                .findFirstByCaissierGuidAndStatut(caissierGuid, StatutSessionCaisse.OUVERTE)
                .orElse(null);
    }

    @Override
    @Transactional
    public SessionCaisseEntity ouvrir(String caissierGuid, BigDecimal fondInitial) {
        if (sessionOuverte(caissierGuid) != null) {
            throw new BadRequestException("Une session de caisse est déjà ouverte pour ce caissier.");
        }
        UserEntity caissier = userRepository.findByGuid(caissierGuid)
                .orElseThrow(() -> new ResourceNotFoundException("Caissier", caissierGuid));

        SessionCaisseEntity s = new SessionCaisseEntity();
        s.setCaissier(caissier);
        s.setFondInitial(fondInitial != null ? fondInitial : BigDecimal.ZERO);
        s.setDateOuverture(LocalDateTime.now());
        s.setStatut(StatutSessionCaisse.OUVERTE);

        SessionCaisseEntity saved = sessionRepository.save(s);
        if (saved.getNumero() == null) {
            saved.setNumero("CAISSE-" + String.format("%05d", saved.getId()));
            saved = sessionRepository.save(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public SessionCaisseEntity fermer(String guid, BigDecimal fondFinalReel, String commentaire) {
        SessionCaisseEntity s = findByGuid(guid);
        if (s.getStatut() != StatutSessionCaisse.OUVERTE) {
            throw new BadRequestException("Cette session est déjà fermée.");
        }

        List<PaiementEntity> paiements =
                paiementRepository.findBySessionCaisseGuidAndStatut(s.getGuid(), StatutPaiement.VALIDE);

        BigDecimal totalEncaisse = BigDecimal.ZERO;
        BigDecimal totalEspeces = BigDecimal.ZERO;
        for (PaiementEntity p : paiements) {
            totalEncaisse = totalEncaisse.add(p.getMontant() != null ? p.getMontant() : BigDecimal.ZERO);
            for (PaiementLigneEntity l : p.getLignes()) {
                if (l.getMode() == ModePaiement.ESPECES && l.getMontant() != null) {
                    totalEspeces = totalEspeces.add(l.getMontant());
                }
            }
        }

        BigDecimal theorique = s.getFondInitial().add(totalEspeces);
        s.setTotalEncaisse(totalEncaisse);
        s.setTotalEspeces(totalEspeces);
        s.setMontantTheorique(theorique);
        s.setFondFinalReel(fondFinalReel);
        s.setEcart(fondFinalReel.subtract(theorique));
        s.setCommentaire(commentaire);
        s.setDateFermeture(LocalDateTime.now());
        s.setStatut(StatutSessionCaisse.FERMEE);
        return sessionRepository.save(s);
    }
}
