package yamrestor.restor.mapper.traiteur;

import yamrestor.restor.dto.traiteur.PrestationDTO;
import yamrestor.restor.dto.traiteur.RentabilitePrestationDTO;
import yamrestor.restor.entity.traiteur.PrestationEntity;

import java.math.BigDecimal;

public class PrestationMapper {

    public static PrestationDTO toDTO(PrestationEntity p) {
        if (p == null) return null;
        BigDecimal total = nz(p.getMontantTotal());
        BigDecimal paye = nz(p.getMontantPaye());
        return PrestationDTO.builder()
                .guid(p.getGuid())
                .numero(p.getNumero())
                .typeEvenement(p.getTypeEvenement())
                .dateEvenement(p.getDateEvenement())
                .heure(p.getHeure())
                .lieu(p.getLieu())
                .clientNom(p.getClientNom())
                .clientTelephone(p.getClientTelephone())
                .responsableGuid(p.getResponsable() != null ? p.getResponsable().getGuid() : null)
                .responsableNom(p.getResponsable() != null ? p.getResponsable().getName() : null)
                .nombreConvivesPrevu(p.getNombreConvivesPrevu())
                .nombreAdultes(p.getNombreAdultes())
                .nombreEnfants(p.getNombreEnfants())
                .observations(p.getObservations())
                .statut(p.getStatut())
                .montantTotal(total)
                .montantPaye(paye)
                .resteAPayer(total.subtract(paye).max(BigDecimal.ZERO))
                .coutMatieresPremieres(nz(p.getCoutMatieresPremieres()))
                .coutPersonnel(nz(p.getCoutPersonnel()))
                .coutTransport(nz(p.getCoutTransport()))
                .coutMateriel(nz(p.getCoutMateriel()))
                .coutDivers(nz(p.getCoutDivers()))
                .build();
    }

    public static RentabilitePrestationDTO toRentabilite(PrestationEntity p) {
        if (p == null) return null;
        BigDecimal ca = nz(p.getMontantTotal());
        BigDecimal mp = nz(p.getCoutMatieresPremieres());
        BigDecimal perso = nz(p.getCoutPersonnel());
        BigDecimal transport = nz(p.getCoutTransport());
        BigDecimal materiel = nz(p.getCoutMateriel());
        BigDecimal divers = nz(p.getCoutDivers());
        BigDecimal coutTotal = mp.add(perso).add(transport).add(materiel).add(divers);
        BigDecimal paye = nz(p.getMontantPaye());
        return RentabilitePrestationDTO.builder()
                .prestationGuid(p.getGuid())
                .numero(p.getNumero())
                .chiffreAffaires(ca)
                .coutMatieresPremieres(mp)
                .coutPersonnel(perso)
                .coutTransport(transport)
                .coutMateriel(materiel)
                .coutDivers(divers)
                .coutTotal(coutTotal)
                .benefice(ca.subtract(coutTotal))
                .montantPaye(paye)
                .resteAPayer(ca.subtract(paye).max(BigDecimal.ZERO))
                .build();
    }

    private static BigDecimal nz(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }

    private PrestationMapper() {}
}
