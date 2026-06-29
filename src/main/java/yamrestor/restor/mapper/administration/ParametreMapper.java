package yamrestor.restor.mapper.administration;

import yamrestor.restor.dto.administration.ParametreDTO;
import yamrestor.restor.entity.administration.ParametreEntity;

public class ParametreMapper {

    public static ParametreDTO toDTO(ParametreEntity p) {
        if (p == null) return null;
        return ParametreDTO.builder()
                .guid(p.getGuid())
                .nomRestaurant(p.getNomRestaurant())
                .slogan(p.getSlogan())
                .logo(p.getLogo())
                .email(p.getEmail())
                .telephone(p.getTelephone())
                .telephone2(p.getTelephone2())
                .adresse(p.getAdresse())
                .ville(p.getVille())
                .pays(p.getPays())
                .siteWeb(p.getSiteWeb())
                .devise(p.getDevise())
                .symboleDevise(p.getSymboleDevise())
                .tauxTvaDefaut(p.getTauxTvaDefaut())
                .numerotationAuto(p.getNumerotationAuto())
                .prefixeCommande(p.getPrefixeCommande())
                .prefixeTicket(p.getPrefixeTicket())
                .prefixeFacture(p.getPrefixeFacture())
                .prefixeReservation(p.getPrefixeReservation())
                .piedPageTicket(p.getPiedPageTicket())
                .conditionsGenerales(p.getConditionsGenerales())
                .build();
    }

    private ParametreMapper() {}
}
