package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.traiteur.PaiementPrestationDTO;
import yamrestor.restor.dto.request.traiteur.PaiementPrestationRequest;
import yamrestor.restor.mapper.traiteur.PaiementPrestationMapper;
import yamrestor.restor.service.traiteur.PaiementPrestationService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paiements-prestation")
@RequiredArgsConstructor
@Tag(name = "Paiements prestation")
@SecurityRequirement(name = "bearerAuth")
public class PaiementPrestationController {

    private final PaiementPrestationService paiementService;

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_PRESTATION_CONSULTER + "')")
    public ResponseEntity<PaiementPrestationDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(PaiementPrestationMapper.toDTO(paiementService.findByGuid(guid)));
    }

    /** Historique des paiements d'une prestation. */
    @GetMapping("/prestation/{prestationGuid}")
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_PRESTATION_CONSULTER + "')")
    public ResponseEntity<List<PaiementPrestationDTO>> parPrestation(@PathVariable String prestationGuid) {
        return ResponseEntity.ok(paiementService.paiementsPrestation(prestationGuid)
                .stream().map(PaiementPrestationMapper::toDTO).toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_PRESTATION_CREER + "')")
    public ResponseEntity<PaiementPrestationDTO> creer(@Valid @RequestBody PaiementPrestationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PaiementPrestationMapper.toDTO(paiementService.creer(req)));
    }

    @PostMapping("/{guid}/annuler")
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_PRESTATION_ANNULER + "')")
    public ResponseEntity<PaiementPrestationDTO> annuler(@PathVariable String guid) {
        return ResponseEntity.ok(PaiementPrestationMapper.toDTO(paiementService.annuler(guid)));
    }
}
