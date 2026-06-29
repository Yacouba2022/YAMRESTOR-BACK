package yamrestor.restor.mapper.salle;

import yamrestor.restor.dto.salle.KdsLigneDTO;
import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.entity.salle.CommandeLigneEntity;

public class KdsMapper {

    public static KdsLigneDTO toDTO(CommandeLigneEntity l) {
        if (l == null) return null;
        CommandeEntity c = l.getCommande();
        return KdsLigneDTO.builder()
                .ligneGuid(l.getGuid())
                .commandeGuid(c != null ? c.getGuid() : null)
                .commandeNumero(c != null ? c.getNumero() : null)
                .tableNumero(c != null && c.getTable() != null ? c.getTable().getNumero() : null)
                .produitNom(l.getProduitNom())
                .quantite(l.getQuantite())
                .notes(l.getNotes())
                .statutPreparation(l.getStatutPreparation())
                .priorite(c != null ? c.getPriorite() : null)
                .commandeDepuis(c != null ? c.getCreatedAt() : null)
                .build();
    }

    private KdsMapper() {}
}
