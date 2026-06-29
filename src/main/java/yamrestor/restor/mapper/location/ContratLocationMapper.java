package yamrestor.restor.mapper.location;

import yamrestor.restor.dto.location.ContratLocationDTO;
import yamrestor.restor.entity.location.ContratLocationEntity;
import yamrestor.restor.entity.location.ContratLocationLigneEntity;
import org.hibernate.Hibernate;

import java.util.List;

public class ContratLocationMapper {

    public static ContratLocationDTO toDTO(ContratLocationEntity c) {
        if (c == null) return null;

        List<ContratLocationDTO.LigneDTO> lignes = null;
        if (c.getLignes() != null && Hibernate.isInitialized(c.getLignes())) {
            lignes = c.getLignes().stream().map(ContratLocationMapper::toLigneDTO).toList();
        }

        return ContratLocationDTO.builder()
                .guid(c.getGuid())
                .numero(c.getNumero())
                .clientNom(c.getClientNom())
                .clientTelephone(c.getClientTelephone())
                .dateDebut(c.getDateDebut())
                .dateFin(c.getDateFin())
                .montantTotal(c.getMontantTotal())
                .cautionTotale(c.getCautionTotale())
                .montantPaye(c.getMontantPaye())
                .statut(c.getStatut())
                .observations(c.getObservations())
                .lignes(lignes)
                .build();
    }

    private static ContratLocationDTO.LigneDTO toLigneDTO(ContratLocationLigneEntity l) {
        return ContratLocationDTO.LigneDTO.builder()
                .guid(l.getGuid())
                .materielGuid(l.getMateriel() != null ? l.getMateriel().getGuid() : null)
                .materielNom(l.getMateriel() != null ? l.getMateriel().getNom() : null)
                .quantite(l.getQuantite())
                .prixUnitaire(l.getPrixUnitaire())
                .montantLigne(l.getMontantLigne())
                .caution(l.getCaution())
                .build();
    }

    private ContratLocationMapper() {}
}
