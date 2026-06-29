package yamrestor.restor.mapper.caisse;

import yamrestor.restor.dto.caisse.PaiementDTO;
import yamrestor.restor.entity.caisse.PaiementEntity;
import yamrestor.restor.entity.caisse.PaiementLigneEntity;
import org.hibernate.Hibernate;

import java.util.List;

public class PaiementMapper {

    public static PaiementDTO toDTO(PaiementEntity p) {
        if (p == null) return null;

        List<PaiementDTO.LigneDTO> lignes = null;
        if (p.getLignes() != null && Hibernate.isInitialized(p.getLignes())) {
            lignes = p.getLignes().stream().map(PaiementMapper::toLigneDTO).toList();
        }

        return PaiementDTO.builder()
                .guid(p.getGuid())
                .numero(p.getNumero())
                .commandeGuid(p.getCommande() != null ? p.getCommande().getGuid() : null)
                .commandeNumero(p.getCommande() != null ? p.getCommande().getNumero() : null)
                .sessionCaisseGuid(p.getSessionCaisse() != null ? p.getSessionCaisse().getGuid() : null)
                .montant(p.getMontant())
                .rendu(p.getRendu())
                .statut(p.getStatut())
                .createdAt(p.getCreatedAt())
                .lignes(lignes)
                .build();
    }

    private static PaiementDTO.LigneDTO toLigneDTO(PaiementLigneEntity l) {
        return PaiementDTO.LigneDTO.builder()
                .guid(l.getGuid())
                .mode(l.getMode())
                .montant(l.getMontant())
                .reference(l.getReference())
                .build();
    }

    private PaiementMapper() {}
}
