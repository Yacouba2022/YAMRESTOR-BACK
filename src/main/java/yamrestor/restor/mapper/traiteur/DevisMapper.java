package yamrestor.restor.mapper.traiteur;

import yamrestor.restor.dto.traiteur.DevisDTO;
import yamrestor.restor.entity.traiteur.DevisEntity;
import yamrestor.restor.entity.traiteur.DevisLigneEntity;
import org.hibernate.Hibernate;

import java.util.List;

public class DevisMapper {

    public static DevisDTO toDTO(DevisEntity d) {
        if (d == null) return null;

        List<DevisDTO.LigneDTO> lignes = null;
        if (d.getLignes() != null && Hibernate.isInitialized(d.getLignes())) {
            lignes = d.getLignes().stream().map(DevisMapper::toLigneDTO).toList();
        }

        return DevisDTO.builder()
                .guid(d.getGuid())
                .numero(d.getNumero())
                .prestationGuid(d.getPrestation() != null ? d.getPrestation().getGuid() : null)
                .prestationNumero(d.getPrestation() != null ? d.getPrestation().getNumero() : null)
                .montantTotal(d.getMontantTotal())
                .statut(d.getStatut())
                .observations(d.getObservations())
                .lignes(lignes)
                .build();
    }

    private static DevisDTO.LigneDTO toLigneDTO(DevisLigneEntity l) {
        return DevisDTO.LigneDTO.builder()
                .guid(l.getGuid())
                .designation(l.getDesignation())
                .menuTraiteurGuid(l.getMenuTraiteur() != null ? l.getMenuTraiteur().getGuid() : null)
                .menuTraiteurNom(l.getMenuTraiteur() != null ? l.getMenuTraiteur().getNom() : null)
                .quantite(l.getQuantite())
                .prixUnitaire(l.getPrixUnitaire())
                .montantLigne(l.getMontantLigne())
                .build();
    }

    private DevisMapper() {}
}
