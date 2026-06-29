package yamrestor.restor.service.impl.salle;

import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.entity.salle.CommandeLigneEntity;
import yamrestor.restor.entity.salle.TableEntity;
import yamrestor.restor.enums.StatutCommande;
import yamrestor.restor.enums.StatutPreparation;
import yamrestor.restor.enums.StatutTable;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.salle.CommandeLigneRepository;
import yamrestor.restor.repository.salle.CommandeRepository;
import yamrestor.restor.repository.salle.TableRepository;
import yamrestor.restor.service.salle.CuisineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuisineServiceImpl implements CuisineService {

    private final CommandeLigneRepository ligneRepository;
    private final CommandeRepository commandeRepository;
    private final TableRepository tableRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CommandeLigneEntity> lignes(StatutPreparation statut) {
        List<StatutPreparation> statuts = (statut != null)
                ? List.of(statut)
                : List.of(StatutPreparation.EN_ATTENTE, StatutPreparation.EN_PREPARATION, StatutPreparation.PRETE);
        return ligneRepository.findPourCuisine(statuts);
    }

    @Override
    @Transactional
    public CommandeLigneEntity changerStatut(String ligneGuid, StatutPreparation statut) {
        CommandeLigneEntity ligne = ligneRepository.findByGuid(ligneGuid)
                .orElseThrow(() -> new ResourceNotFoundException("CommandeLigne", ligneGuid));
        ligne.setStatutPreparation(statut);
        ligneRepository.save(ligne);

        if (statut == StatutPreparation.SERVIE && ligne.getCommande() != null) {
            cloturerSiTousServis(ligne.getCommande().getGuid());
        }
        return ligne;
    }

    @Override
    @Transactional
    public CommandeLigneEntity avancer(String ligneGuid) {
        CommandeLigneEntity ligne = ligneRepository.findByGuid(ligneGuid)
                .orElseThrow(() -> new ResourceNotFoundException("CommandeLigne", ligneGuid));
        return changerStatut(ligneGuid, suivant(ligne.getStatutPreparation()));
    }

    private StatutPreparation suivant(StatutPreparation s) {
        return switch (s) {
            case EN_ATTENTE -> StatutPreparation.EN_PREPARATION;
            case EN_PREPARATION -> StatutPreparation.PRETE;
            case PRETE, SERVIE -> StatutPreparation.SERVIE;
        };
    }

    /** Si toutes les lignes d'une commande sont servies, la commande passe TERMINEE et libère la table. */
    private void cloturerSiTousServis(String commandeGuid) {
        CommandeEntity c = commandeRepository.findByGuid(commandeGuid).orElse(null);
        if (c == null) return;
        boolean tousServis = !c.getLignes().isEmpty()
                && c.getLignes().stream().allMatch(l -> l.getStatutPreparation() == StatutPreparation.SERVIE);
        if (tousServis) {
            c.setStatut(StatutCommande.TERMINEE);
            TableEntity t = c.getTable();
            if (t != null && t.getStatut() == StatutTable.OCCUPEE) {
                t.setStatut(StatutTable.LIBRE);
                tableRepository.save(t);
            }
            commandeRepository.save(c);
        }
    }
}
