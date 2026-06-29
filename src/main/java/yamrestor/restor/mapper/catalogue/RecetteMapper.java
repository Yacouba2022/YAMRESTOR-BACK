package yamrestor.restor.mapper.catalogue;

import yamrestor.restor.dto.catalogue.RecetteDTO;
import yamrestor.restor.entity.catalogue.RecetteEntity;
import yamrestor.restor.entity.catalogue.RecetteLigneEntity;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.List;

public class RecetteMapper {

    public static RecetteDTO toDTO(RecetteEntity r) {
        if (r == null) return null;

        BigDecimal prixVente = r.getProduit() != null ? r.getProduit().getPrixVente() : null;

        List<RecetteDTO.LigneDTO> lignes = null;
        BigDecimal cout = BigDecimal.ZERO;
        if (r.getLignes() != null && Hibernate.isInitialized(r.getLignes())) {
            lignes = r.getLignes().stream().map(RecetteMapper::toLigneDTO).toList();
            for (RecetteDTO.LigneDTO l : lignes) {
                if (l.getCoutLigne() != null) cout = cout.add(l.getCoutLigne());
            }
        }

        BigDecimal marge = (prixVente != null) ? prixVente.subtract(cout) : null;

        return RecetteDTO.builder()
                .guid(r.getGuid())
                .produitGuid(r.getProduit() != null ? r.getProduit().getGuid() : null)
                .produitNom(r.getProduit() != null ? r.getProduit().getNom() : null)
                .prixVente(prixVente)
                .tempsPreparation(r.getTempsPreparation())
                .instructions(r.getInstructions())
                .coutRevient(cout)
                .marge(marge)
                .lignes(lignes)
                .build();
    }

    private static RecetteDTO.LigneDTO toLigneDTO(RecetteLigneEntity l) {
        BigDecimal prixAchat = l.getMatierePremiere() != null ? l.getMatierePremiere().getPrixAchat() : null;
        BigDecimal coutLigne = (prixAchat != null && l.getQuantite() != null)
                ? prixAchat.multiply(l.getQuantite()) : null;
        return RecetteDTO.LigneDTO.builder()
                .guid(l.getGuid())
                .matierePremiereGuid(l.getMatierePremiere() != null ? l.getMatierePremiere().getGuid() : null)
                .matierePremiereNom(l.getMatierePremiere() != null ? l.getMatierePremiere().getNom() : null)
                .quantite(l.getQuantite())
                .uniteGuid(l.getUnite() != null ? l.getUnite().getGuid() : null)
                .uniteNom(l.getUnite() != null ? l.getUnite().getNom() : null)
                .prixAchatUnitaire(prixAchat)
                .coutLigne(coutLigne)
                .build();
    }

    private RecetteMapper() {}
}
