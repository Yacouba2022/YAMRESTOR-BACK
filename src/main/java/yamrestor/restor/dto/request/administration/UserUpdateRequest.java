package yamrestor.restor.dto.request.administration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank
    private String name;

    private String fonction;
    private String telephone;
    private String adresse;
    private String compte;
    private String etat;
    private String profilGuid;
}
