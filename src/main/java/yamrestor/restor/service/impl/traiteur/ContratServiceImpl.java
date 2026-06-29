package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.entity.traiteur.ContratEntity;
import yamrestor.restor.enums.StatutContrat;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.traiteur.ContratRepository;
import yamrestor.restor.service.traiteur.ContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements ContratService {

    private final ContratRepository contratRepository;

    @Override
    public Page<ContratEntity> search(StatutContrat statut, int page, int size) {
        return contratRepository.search(statut, PageRequest.of(page, size));
    }

    @Override
    public ContratEntity findByGuid(String guid) {
        return contratRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("ContratEntity", guid));
    }

    @Override
    @Transactional
    public ContratEntity signer(String guid) {
        ContratEntity c = findByGuid(guid);
        if (c.getStatut() == StatutContrat.ANNULE) {
            throw new BadRequestException("Un contrat annulé ne peut pas être signé.");
        }
        c.setStatut(StatutContrat.SIGNE);
        c.setDateSignature(LocalDate.now());
        return contratRepository.save(c);
    }

    @Override
    @Transactional
    public ContratEntity annuler(String guid) {
        ContratEntity c = findByGuid(guid);
        c.setStatut(StatutContrat.ANNULE);
        return contratRepository.save(c);
    }
}
