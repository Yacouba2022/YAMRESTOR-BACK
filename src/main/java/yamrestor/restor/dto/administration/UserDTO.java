package yamrestor.restor.dto.administration;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private String guid;
    private String name;
    private String email;
    private String fonction;
    private String telephone;
    private String adresse;
    private String compte;
    private String etat;
    private String profilePhotoPath;
    private String preferences;
    private String profilGuid;
    private String profilNom;
}
