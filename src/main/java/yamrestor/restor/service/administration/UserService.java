package yamrestor.restor.service.administration;

import yamrestor.restor.dto.request.administration.UserCreateRequest;
import yamrestor.restor.dto.request.administration.UserUpdateRequest;
import yamrestor.restor.entity.administration.UserEntity;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface UserService {
    Page<UserEntity> findAll(int page, int size);
    UserEntity findByGuid(String guid);

    /** Construit un UserEntity depuis le DTO de création, résout le profil, puis sauvegarde. */
    UserEntity creerDepuisRequest(UserCreateRequest req);

    /** Met à jour un utilisateur depuis le DTO de modification, résout le profil, puis sauvegarde. */
    UserEntity modifierDepuisRequest(String guid, UserUpdateRequest req);

    void changerMotDePasse(String guid, String nouveauMotDePasse);

    /** Sauvegarde les préférences d'affichage (thème, menu…) sérialisées en JSON. */
    UserEntity sauvegarderPreferences(String guid, Map<String, Object> prefs);

    /** Invalide les jetons de l'utilisateur et le passe en état inactif. */
    void deconnecter(String guid);

    /** Change l'état (actif/inactif) de l'utilisateur et sauvegarde. */
    UserEntity toggleEtat(String guid, String etat);

    /** Suppression logique de l'utilisateur. */
    void supprimer(String guid);
}
