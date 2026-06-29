package yamrestor.restor.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private String guid;
    private String name;
    private String email;

    /** Permissions effectives de l'utilisateur (codes), pour le contrôle d'affichage côté front. */
    private List<String> permissions;

    /** Vrai si l'utilisateur est super administrateur. */
    private boolean superAdmin;

    /** Délai d'inactivité (en minutes) au-delà duquel le front doit déconnecter automatiquement. */
    private int inactivityTimeoutMinutes;
}
