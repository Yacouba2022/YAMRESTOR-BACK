package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.traiteur.PrestationDTO;
import yamrestor.restor.dto.traiteur.RentabilitePrestationDTO;
import yamrestor.restor.dto.request.traiteur.PrestationRequest;
import yamrestor.restor.enums.StatutPrestation;
import yamrestor.restor.mapper.traiteur.PrestationMapper;
import yamrestor.restor.service.traiteur.PrestationService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/prestations")
@RequiredArgsConstructor
@Tag(name = "Prestations traiteur")
@SecurityRequirement(name = "bearerAuth")
public class PrestationController {

    private final PrestationService prestationService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.PRESTATION_CONSULTER + "')")
    public ResponseEntity<Page<PrestationDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutPrestation statut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(prestationService.search(statut, date, page, size).map(PrestationMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PRESTATION_CONSULTER + "')")
    public ResponseEntity<PrestationDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(PrestationMapper.toDTO(prestationService.findByGuid(guid)));
    }

    /** Rentabilité de la prestation (CA − coûts = bénéfice). */
    @GetMapping("/{guid}/rentabilite")
    @PreAuthorize("hasAuthority('" + Permissions.PRESTATION_CONSULTER + "')")
    public ResponseEntity<RentabilitePrestationDTO> rentabilite(@PathVariable String guid) {
        return ResponseEntity.ok(PrestationMapper.toRentabilite(prestationService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.PRESTATION_CREER + "')")
    public ResponseEntity<PrestationDTO> creer(@Valid @RequestBody PrestationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PrestationMapper.toDTO(prestationService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PRESTATION_MODIFIER + "')")
    public ResponseEntity<PrestationDTO> modifier(@PathVariable String guid,
                                                   @Valid @RequestBody PrestationRequest req) {
        return ResponseEntity.ok(PrestationMapper.toDTO(prestationService.modifierDepuisRequest(guid, req)));
    }

    @PatchMapping("/{guid}/statut")
    @PreAuthorize("hasAuthority('" + Permissions.PRESTATION_CHANGER_STATUT + "')")
    public ResponseEntity<PrestationDTO> changerStatut(@PathVariable String guid,
                                                        @RequestBody Map<String, String> body) {
        StatutPrestation statut = StatutPrestation.valueOf(body.get("statut"));
        return ResponseEntity.ok(PrestationMapper.toDTO(prestationService.changerStatut(guid, statut)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PRESTATION_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        prestationService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
