package yamrestor.restor.controller.administration;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.administration.ProfilDTO;
import yamrestor.restor.dto.request.administration.ProfilRequest;
import yamrestor.restor.service.administration.ProfilService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profils")
@RequiredArgsConstructor
@Tag(name = "Profils")
@SecurityRequirement(name = "bearerAuth")
public class ProfilController {

    private final ProfilService profilService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.PROFIL_CONSULTER + "')")
    public ResponseEntity<Page<ProfilDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(profilService.findAll(page, size));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PROFIL_CONSULTER + "')")
    public ResponseEntity<ProfilDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(profilService.findByGuid(guid));
    }

    /** Autocomplete pour les selects (7 résultats par défaut, recherche via ?q=). */
    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.PROFIL_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(profilService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.PROFIL_CREER + "')")
    public ResponseEntity<ProfilDTO> creer(@Valid @RequestBody ProfilRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profilService.creerDepuisRequest(req));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PROFIL_MODIFIER + "')")
    public ResponseEntity<ProfilDTO> modifier(@PathVariable String guid, @Valid @RequestBody ProfilRequest req) {
        return ResponseEntity.ok(profilService.modifierDepuisRequest(guid, req));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PROFIL_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        profilService.supprimer(guid);
        return ResponseEntity.noContent().build();
    }

    /** Duplique un profil (nom suffixé « (Copie) ») avec ses rôles et permissions additionnelles. */
    @PostMapping("/{guid}/dupliquer")
    @PreAuthorize("hasAuthority('" + Permissions.PROFIL_DUPLIQUER + "')")
    public ResponseEntity<ProfilDTO> dupliquer(@PathVariable String guid) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profilService.dupliquer(guid));
    }
}
