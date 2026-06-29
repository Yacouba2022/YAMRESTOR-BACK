package yamrestor.restor.service.impl.crm;

import yamrestor.restor.entity.crm.BonAchatEntity;
import yamrestor.restor.entity.crm.ClientEntity;
import yamrestor.restor.enums.StatutBonAchat;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.crm.BonAchatRepository;
import yamrestor.restor.repository.crm.ClientRepository;
import yamrestor.restor.service.crm.FideliteService;
import yamrestor.restor.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FideliteServiceImpl implements FideliteService {

    private final ClientRepository clientRepository;
    private final BonAchatRepository bonAchatRepository;

    @Override
    @Transactional
    public ClientEntity ajouterPoints(String clientGuid, int points) {
        ClientEntity c = resolveClient(clientGuid);
        c.setPointsFidelite(nz(c.getPointsFidelite()) + points);
        return clientRepository.save(c);
    }

    @Override
    @Transactional
    public ClientEntity retirerPoints(String clientGuid, int points) {
        ClientEntity c = resolveClient(clientGuid);
        if (nz(c.getPointsFidelite()) < points) {
            throw new BadRequestException("Solde de points insuffisant.");
        }
        c.setPointsFidelite(nz(c.getPointsFidelite()) - points);
        return clientRepository.save(c);
    }

    @Override
    @Transactional
    public BonAchatEntity convertirEnBon(String clientGuid, int points) {
        ClientEntity c = resolveClient(clientGuid);
        if (nz(c.getPointsFidelite()) < points) {
            throw new BadRequestException("Solde de points insuffisant pour cette conversion.");
        }
        c.setPointsFidelite(nz(c.getPointsFidelite()) - points);
        clientRepository.save(c);

        BonAchatEntity bon = new BonAchatEntity();
        bon.setClient(c);
        bon.setPointsUtilises(points);
        bon.setMontant(Constants.VALEUR_POINT.multiply(BigDecimal.valueOf(points)));
        bon.setStatut(StatutBonAchat.DISPONIBLE);
        bon.setDateExpiration(LocalDate.now().plusDays(Constants.BON_ACHAT_VALIDITE_JOURS));

        BonAchatEntity saved = bonAchatRepository.save(bon);
        if (saved.getCode() == null) {
            saved.setCode("BON-" + String.format("%05d", saved.getId()));
            saved = bonAchatRepository.save(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public BonAchatEntity utiliserBon(String bonGuid) {
        BonAchatEntity bon = bonAchatRepository.findByGuid(bonGuid)
                .orElseThrow(() -> new ResourceNotFoundException("BonAchat", bonGuid));
        if (bon.getStatut() != StatutBonAchat.DISPONIBLE) {
            throw new BadRequestException("Ce bon d'achat n'est pas disponible.");
        }
        if (bon.getDateExpiration() != null && bon.getDateExpiration().isBefore(LocalDate.now())) {
            bon.setStatut(StatutBonAchat.EXPIRE);
            bonAchatRepository.save(bon);
            throw new BadRequestException("Ce bon d'achat est expiré.");
        }
        bon.setStatut(StatutBonAchat.UTILISE);
        return bonAchatRepository.save(bon);
    }

    @Override
    public List<BonAchatEntity> bonsClient(String clientGuid) {
        return bonAchatRepository.findByClientGuidOrderByCreatedAtDesc(clientGuid);
    }

    private ClientEntity resolveClient(String guid) {
        return clientRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Client", guid));
    }

    private int nz(Integer v) {
        return v != null ? v : 0;
    }
}
