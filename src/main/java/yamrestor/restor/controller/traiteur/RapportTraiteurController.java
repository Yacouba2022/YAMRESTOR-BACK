package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.traiteur.RapportTraiteurDTO;
import yamrestor.restor.service.traiteur.RapportTraiteurService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/rapports-traiteur")
@RequiredArgsConstructor
@Tag(name = "Rapports traiteur")
@SecurityRequirement(name = "bearerAuth")
public class RapportTraiteurController {

    private final RapportTraiteurService rapportTraiteurService;

    /** Synthèse des prestations (nb, statuts, CA, coûts, bénéfice) sur une période. */
    @GetMapping("/synthese")
    @PreAuthorize("hasAuthority('" + Permissions.RAPPORT_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<RapportTraiteurDTO> synthese(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportTraiteurService.synthese(debut, fin));
    }
}
