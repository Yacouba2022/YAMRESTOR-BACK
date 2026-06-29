package yamrestor.restor.service.impl.livraison;

import yamrestor.restor.dto.request.livraison.LivraisonRequest;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.entity.livraison.LivraisonEntity;
import yamrestor.restor.entity.livraison.ZoneLivraisonEntity;
import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.enums.StatutLivraison;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.administration.UserRepository;
import yamrestor.restor.repository.livraison.LivraisonRepository;
import yamrestor.restor.repository.livraison.ZoneLivraisonRepository;
import yamrestor.restor.repository.salle.CommandeRepository;
import yamrestor.restor.service.livraison.LivraisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LivraisonServiceImpl implements LivraisonService {

    private final LivraisonRepository livraisonRepository;
    private final CommandeRepository commandeRepository;
    private final ZoneLivraisonRepository zoneRepository;
    private final UserRepository userRepository;

    @Override
    public Page<LivraisonEntity> search(StatutLivraison statut, int page, int size) {
        return livraisonRepository.search(statut, PageRequest.of(page, size));
    }

    @Override
    public LivraisonEntity findByGuid(String guid) {
        return livraisonRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("LivraisonEntity", guid));
    }

    @Override
    @Transactional
    public LivraisonEntity creerDepuisRequest(LivraisonRequest req) {
        LivraisonEntity l = new LivraisonEntity();
        l.setCommande(resolveCommande(req.getCommandeGuid()));
        l.setStatut(StatutLivraison.EN_ATTENTE);
        apply(l, req);
        return livraisonRepository.save(l);
    }

    @Override
    @Transactional
    public LivraisonEntity modifierDepuisRequest(String guid, LivraisonRequest req) {
        LivraisonEntity l = findByGuid(guid);
        apply(l, req);
        return livraisonRepository.save(l);
    }

    @Override
    @Transactional
    public LivraisonEntity changerStatut(String guid, StatutLivraison statut) {
        LivraisonEntity l = findByGuid(guid);
        l.setStatut(statut);
        if (statut == StatutLivraison.LIVREE) {
            l.setDateLivraison(LocalDateTime.now());
        }
        return livraisonRepository.save(l);
    }

    private void apply(LivraisonEntity l, LivraisonRequest req) {
        ZoneLivraisonEntity zone = resolveZone(req.getZoneGuid());
        l.setLivreur(resolveLivreur(req.getLivreurGuid()));
        l.setZone(zone);
        BigDecimal frais = req.getFrais();
        if (frais == null) {
            frais = zone != null && zone.getFrais() != null ? zone.getFrais() : BigDecimal.ZERO;
        }
        l.setFrais(frais);
        l.setAdresse(req.getAdresse());
        l.setTelephone(req.getTelephone());
        l.setCommentaire(req.getCommentaire());
    }

    private CommandeEntity resolveCommande(String guid) {
        return commandeRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", guid));
    }

    private ZoneLivraisonEntity resolveZone(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return zoneRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("ZoneLivraison", guid));
    }

    private UserEntity resolveLivreur(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return userRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur", guid));
    }
}
