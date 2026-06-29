package yamrestor.restor.mapper.approvisionnement;

import yamrestor.restor.dto.approvisionnement.PaiementAchatDTO;
import yamrestor.restor.entity.approvisionnement.PaiementAchatEntity;

public class PaiementAchatMapper {

    public static PaiementAchatDTO toDTO(PaiementAchatEntity p) {
        if (p == null) return null;
        return PaiementAchatDTO.builder()
                .guid(p.getGuid())
                .numero(p.getNumero())
                .fournisseurGuid(p.getFournisseur() != null ? p.getFournisseur().getGuid() : null)
                .fournisseurNom(p.getFournisseur() != null ? p.getFournisseur().getNom() : null)
                .commandeAchatGuid(p.getCommandeAchat() != null ? p.getCommandeAchat().getGuid() : null)
                .commandeAchatNumero(p.getCommandeAchat() != null ? p.getCommandeAchat().getNumero() : null)
                .montant(p.getMontant())
                .mode(p.getMode())
                .reference(p.getReference())
                .statut(p.getStatut())
                .createdAt(p.getCreatedAt())
                .build();
    }

    private PaiementAchatMapper() {}
}
