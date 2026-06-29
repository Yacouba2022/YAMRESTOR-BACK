package yamrestor.restor.controller.administration;

import yamrestor.restor.dto.administration.ParametreDTO;
import yamrestor.restor.dto.request.administration.ParametreRequest;
import yamrestor.restor.service.administration.ParametreService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parametres")
@RequiredArgsConstructor
@Tag(name = "Paramètres")
@SecurityRequirement(name = "bearerAuth")
public class ParametreController {

    private final ParametreService service;

    /** Retourne les paramètres du restaurant (créés par défaut au premier appel). */
    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.PARAMETRE_CONSULTER + "')")
    public ResponseEntity<ParametreDTO> get() {
        return ResponseEntity.ok(service.get());
    }

    /** Met à jour les paramètres du restaurant. */
    @PutMapping
    @PreAuthorize("hasAuthority('" + Permissions.PARAMETRE_GERER + "')")
    public ResponseEntity<ParametreDTO> mettreAJour(@Valid @RequestBody ParametreRequest req) {
        return ResponseEntity.ok(service.mettreAJour(req));
    }
}
