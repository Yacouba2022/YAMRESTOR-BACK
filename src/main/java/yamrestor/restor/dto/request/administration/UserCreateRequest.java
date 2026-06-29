package yamrestor.restor.dto.request.administration;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @NotBlank @Email(message = "Email invalide")
    private String email;

    @NotBlank @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères")
    private String password;

    private String fonction;
    private String telephone;
    private String adresse;
    private String compte;
    private String etat = "actif";

    @NotBlank(message = "Le profil est obligatoire")
    private String profilGuid;
}
