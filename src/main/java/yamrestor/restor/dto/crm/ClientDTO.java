package yamrestor.restor.dto.crm;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClientDTO {
    private String guid;
    private String nom;
    private String telephone;
    private String email;
    private String adresse;
    private LocalDate dateNaissance;
    private String preferences;
    private Integer pointsFidelite;
    private BigDecimal remisePourcentage;
    private Boolean actif;
}
