package yamrestor.restor.mapper.approvisionnement;

import yamrestor.restor.dto.approvisionnement.CommandeAchatDTO;
import yamrestor.restor.entity.approvisionnement.CommandeAchatEntity;
import yamrestor.restor.entity.approvisionnement.CommandeAchatLigneEntity;
import org.hibernate.Hibernate;

import java.util.List;

public class CommandeAchatMapper {

    public static CommandeAchatDTO toDTO(CommandeAchatEntity c) {
        if (c == null) return null;

        List<CommandeAchatDTO.LigneDTO> lignes = null;
        if (c.getLignes() != null && Hibernate.isInitialized(c.getLignes())) {
            lignes = c.getLignes().stream().map(CommandeAchatMapper::toLigneDTO).toList();
        }

        return CommandeAchatDTO.builder()
                .guid(c.getGuid())
                .numero(c.getNumero())
                .fournisseurGuid(c.getFournisseur() != null ? c.getFournisseur().getGuid() : null)
                .fournisseurNom(c.getFournisseur() != null ? c.getFournisseur().getNom() : null)
                .statut(c.getStatut())
                .montantTotal(c.getMontantTotal())
                .dateReception(c.getDateReception())
                .observations(c.getObservations())
                .lignes(lignes)
                .build();
    }

    private static CommandeAchatDTO.LigneDTO toLigneDTO(CommandeAchatLigneEntity l) {
        return CommandeAchatDTO.LigneDTO.builder()
                .guid(l.getGuid())
                .matierePremiereGuid(l.getMatierePremiere() != null ? l.getMatierePremiere().getGuid() : null)
                .matierePremiereNom(l.getMatierePremiere() != null ? l.getMatierePremiere().getNom() : null)
                .quantite(l.getQuantite())
                .prixUnitaire(l.getPrixUnitaire())
                .montantLigne(l.getMontantLigne())
                .build();
    }

    private CommandeAchatMapper() {}
}
