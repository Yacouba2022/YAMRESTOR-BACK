package yamrestor.restor.dto.administration;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParametreDTO {
    private String guid;
    private String nomRestaurant;
    private String slogan;
    private String logo;
    private String email;
    private String telephone;
    private String telephone2;
    private String adresse;
    private String ville;
    private String pays;
    private String siteWeb;
    private String devise;
    private String symboleDevise;
    private BigDecimal tauxTvaDefaut;
    private Boolean numerotationAuto;
    private String prefixeCommande;
    private String prefixeTicket;
    private String prefixeFacture;
    private String prefixeReservation;
    private String piedPageTicket;
    private String conditionsGenerales;
}
