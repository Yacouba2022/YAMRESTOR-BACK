package yamrestor.restor.mapper.catalogue;

import yamrestor.restor.dto.catalogue.MatierePremiereDTO;
import yamrestor.restor.entity.catalogue.MatierePremiereEntity;

import java.math.BigDecimal;

public class MatierePremiereMapper {

    public static MatierePremiereDTO toDTO(MatierePremiereEntity m) {
        if (m == null) return null;
        boolean stockFaible = m.getSeuilAlerte() != null && m.getStock() != null
                && m.getStock().compareTo(m.getSeuilAlerte()) <= 0;
        return MatierePremiereDTO.builder()
                .guid(m.getGuid())
                .nom(m.getNom())
                .code(m.getCode())
                .uniteGuid(m.getUnite() != null ? m.getUnite().getGuid() : null)
                .uniteNom(m.getUnite() != null ? m.getUnite().getNom() : null)
                .prixAchat(m.getPrixAchat())
                .stock(m.getStock() != null ? m.getStock() : BigDecimal.ZERO)
                .stockMinimum(m.getStockMinimum())
                .seuilAlerte(m.getSeuilAlerte())
                .datePeremption(m.getDatePeremption())
                .emplacement(m.getEmplacement())
                .actif(m.getActif())
                .stockFaible(stockFaible)
                .build();
    }

    private MatierePremiereMapper() {}
}
