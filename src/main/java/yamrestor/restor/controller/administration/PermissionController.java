package yamrestor.restor.controller.administration;

import yamrestor.restor.dto.administration.PermissionDTO;
import yamrestor.restor.dto.request.administration.PermissionRequest;
import yamrestor.restor.service.administration.PermissionService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Tag(name = "Permissions")
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.PERMISSION_CONSULTER + "')")
    public ResponseEntity<Page<PermissionDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "200") int size) {
        return ResponseEntity.ok(permissionService.findAll(page, size));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PERMISSION_CONSULTER + "')")
    public ResponseEntity<PermissionDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(permissionService.findByGuid(guid));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.PERMISSION_CREER + "')")
    public ResponseEntity<PermissionDTO> creer(@Valid @RequestBody PermissionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.creerDepuisRequest(req));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PERMISSION_MODIFIER + "')")
    public ResponseEntity<PermissionDTO> modifier(@PathVariable String guid, @Valid @RequestBody PermissionRequest req) {
        return ResponseEntity.ok(permissionService.modifierDepuisRequest(guid, req));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PERMISSION_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        permissionService.supprimer(guid);
        return ResponseEntity.noContent().build();
    }
}
