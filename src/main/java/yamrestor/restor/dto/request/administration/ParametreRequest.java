package yamrestor.restor.dto.request.administration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ParametreRequest {

    @NotBlank(message = "Le nom du restaurant est obligatoire")
    private String nomRestaurant;

    private String slogan;
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
