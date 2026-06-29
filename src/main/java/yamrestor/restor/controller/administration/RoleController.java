package yamrestor.restor.controller.administration;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.administration.RoleDTO;
import yamrestor.restor.dto.request.administration.RoleRequest;
import yamrestor.restor.service.administration.RoleService;
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
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Rôles")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.ROLE_CONSULTER + "')")
    public ResponseEntity<Page<RoleDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(roleService.findAll(page, size));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.ROLE_CONSULTER + "')")
    public ResponseEntity<RoleDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(roleService.findByGuid(guid));
    }

    /** Autocomplete pour les selects (7 résultats par défaut, recherche via ?q=). */
    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.ROLE_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(roleService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.ROLE_CREER + "')")
    public ResponseEntity<RoleDTO> creer(@Valid @RequestBody RoleRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.creerDepuisRequest(req));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.ROLE_MODIFIER + "')")
    public ResponseEntity<RoleDTO> modifier(@PathVariable String guid, @Valid @RequestBody RoleRequest req) {
        return ResponseEntity.ok(roleService.modifierDepuisRequest(guid, req));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.ROLE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        roleService.supprimer(guid);
        return ResponseEntity.noContent().build();
    }
}
