package yamrestor.restor.mapper.livraison;

import yamrestor.restor.dto.livraison.LivraisonDTO;
import yamrestor.restor.entity.livraison.LivraisonEntity;

public class LivraisonMapper {

    public static LivraisonDTO toDTO(LivraisonEntity l) {
        if (l == null) return null;
        return LivraisonDTO.builder()
                .guid(l.getGuid())
                .commandeGuid(l.getCommande() != null ? l.getCommande().getGuid() : null)
                .commandeNumero(l.getCommande() != null ? l.getCommande().getNumero() : null)
                .livreurGuid(l.getLivreur() != null ? l.getLivreur().getGuid() : null)
                .livreurNom(l.getLivreur() != null ? l.getLivreur().getName() : null)
                .zoneGuid(l.getZone() != null ? l.getZone().getGuid() : null)
                .zoneNom(l.getZone() != null ? l.getZone().getNom() : null)
                .frais(l.getFrais())
                .adresse(l.getAdresse())
                .telephone(l.getTelephone())
                .statut(l.getStatut())
                .dateLivraison(l.getDateLivraison())
                .commentaire(l.getCommentaire())
                .build();
    }

    private LivraisonMapper() {}
}
