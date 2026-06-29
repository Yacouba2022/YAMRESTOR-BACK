package yamrestor.restor.service.traiteur;

import yamrestor.restor.entity.traiteur.ContratEntity;
import yamrestor.restor.enums.StatutContrat;
import org.springframework.data.domain.Page;

public interface ContratService {
    Page<ContratEntity> search(StatutContrat statut, int page, int size);
    ContratEntity findByGuid(String guid);
    ContratEntity signer(String guid);
    ContratEntity annuler(String guid);
}
