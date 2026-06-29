package yamrestor.restor.controller.administration;

import yamrestor.restor.dto.administration.UserDTO;
import yamrestor.restor.dto.request.administration.UserCreateRequest;
import yamrestor.restor.dto.request.administration.UserUpdateRequest;
import yamrestor.restor.mapper.administration.UserMapper;
import yamrestor.restor.service.administration.UserService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.USER_CONSULTER + "')")
    public ResponseEntity<Page<UserDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(userService.findAll(page, size).map(UserMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.USER_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<UserDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.USER_CREER + "')")
    public ResponseEntity<UserDTO> creer(@Valid @RequestBody UserCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.toDTO(userService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.USER_MODIFIER + "')")
    public ResponseEntity<UserDTO> modifier(@PathVariable String guid,
                                             @Valid @RequestBody UserUpdateRequest req) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.modifierDepuisRequest(guid, req)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.USER_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        userService.supprimer(guid);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{guid}/password")
    @PreAuthorize("hasAuthority('" + Permissions.USER_CHANGER_MOT_DE_PASSE + "')")
    public ResponseEntity<Void> changerMotDePasse(@PathVariable String guid,
                                                   @RequestParam String nouveauMotDePasse) {
        userService.changerMotDePasse(guid, nouveauMotDePasse);
        return ResponseEntity.noContent().build();
    }

    /**
     * Déconnecte de force un utilisateur ET désactive son compte : tous ses jetons en cours
     * deviennent invalides (401 au prochain appel) et il ne pourra plus se reconnecter tant
     * que son compte n'aura pas été réactivé.
     */
    @PostMapping("/{guid}/deconnecter")
    @PreAuthorize("hasAuthority('" + Permissions.USER_DECONNECTER + "')")
    public ResponseEntity<Void> deconnecter(@PathVariable String guid) {
        userService.deconnecter(guid);
        return ResponseEntity.noContent().build();
    }

    /** Toggle rapide de l'état (actif ↔ inactif). */
    @PatchMapping("/{guid}/etat")
    @PreAuthorize("hasAuthority('" + Permissions.USER_ACTIVER_DESACTIVER + "')")
    public ResponseEntity<UserDTO> toggleEtat(@PathVariable String guid,
                                               @RequestBody Map<String, String> body) {
        String etat = body.getOrDefault("etat", "actif");
        return ResponseEntity.ok(UserMapper.toDTO(userService.toggleEtat(guid, etat)));
    }

    /** Sauvegarde les préférences d'affichage (thème, menu, etc.) de l'utilisateur. */
    @PatchMapping("/{guid}/preferences")
    public ResponseEntity<Void> sauvegarderPreferences(@PathVariable String guid,
                                                        @RequestBody Map<String, Object> prefs) {
        userService.sauvegarderPreferences(guid, prefs);
        return ResponseEntity.noContent().build();
    }
}
