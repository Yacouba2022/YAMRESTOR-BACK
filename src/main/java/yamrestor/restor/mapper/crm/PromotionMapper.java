package yamrestor.restor.mapper.crm;

import yamrestor.restor.dto.crm.PromotionDTO;
import yamrestor.restor.entity.crm.PromotionEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class PromotionMapper {

    public static PromotionDTO toDTO(PromotionEntity p, LocalDate jour, LocalTime heure) {
        if (p == null) return null;
        return PromotionDTO.builder()
                .guid(p.getGuid())
                .nom(p.getNom())
                .type(p.getType())
                .valeur(p.getValeur())
                .code(p.getCode())
                .dateDebut(p.getDateDebut())
                .dateFin(p.getDateFin())
                .heureDebut(p.getHeureDebut())
                .heureFin(p.getHeureFin())
                .actif(p.getActif())
                .activeMaintenant(p.estActive(jour, heure))
                .build();
    }

    private PromotionMapper() {}
}
