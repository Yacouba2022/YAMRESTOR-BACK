package yamrestor.restor.mapper.salle;

import yamrestor.restor.dto.salle.CommandeDTO;
import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.entity.salle.CommandeLigneEntity;
import org.hibernate.Hibernate;

import java.util.List;

public class CommandeMapper {

    public static CommandeDTO toDTO(CommandeEntity c) {
        if (c == null) return null;

        List<CommandeDTO.LigneDTO> lignes = null;
        if (c.getLignes() != null && Hibernate.isInitialized(c.getLignes())) {
            lignes = c.getLignes().stream().map(CommandeMapper::toLigneDTO).toList();
        }

        return CommandeDTO.builder()
                .guid(c.getGuid())
                .numero(c.getNumero())
                .type(c.getType())
                .tableGuid(c.getTable() != null ? c.getTable().getGuid() : null)
                .tableNumero(c.getTable() != null ? c.getTable().getNumero() : null)
                .serveurGuid(c.getServeur() != null ? c.getServeur().getGuid() : null)
                .serveurNom(c.getServeur() != null ? c.getServeur().getName() : null)
                .clientNom(c.getClientNom())
                .clientTelephone(c.getClientTelephone())
                .statut(c.getStatut())
                .priorite(c.getPriorite())
                .observations(c.getObservations())
                .montantTotal(c.getMontantTotal())
                .createdAt(c.getCreatedAt())
                .lignes(lignes)
                .build();
    }

    private static CommandeDTO.LigneDTO toLigneDTO(CommandeLigneEntity l) {
        return CommandeDTO.LigneDTO.builder()
                .guid(l.getGuid())
                .produitGuid(l.getProduit() != null ? l.getProduit().getGuid() : null)
                .produitNom(l.getProduitNom())
                .quantite(l.getQuantite())
                .prixUnitaire(l.getPrixUnitaire())
                .montantLigne(l.getMontantLigne())
                .notes(l.getNotes())
                .statutPreparation(l.getStatutPreparation())
                .build();
    }

    private CommandeMapper() {}
}
