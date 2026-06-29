package yamrestor.restor.controller.location;

import yamrestor.restor.dto.location.ContratLocationDTO;
import yamrestor.restor.dto.request.location.ContratLocationRequest;
import yamrestor.restor.enums.StatutContratLocation;
import yamrestor.restor.mapper.location.ContratLocationMapper;
import yamrestor.restor.service.location.ContratLocationService;
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

@RestController
@RequestMapping("/api/v1/contrats-location")
@RequiredArgsConstructor
@Tag(name = "Contrats de location")
@SecurityRequirement(name = "bearerAuth")
public class ContratLocationController {

    private final ContratLocationService contratService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.CONTRAT_LOCATION_CONSULTER + "')")
    public ResponseEntity<Page<ContratLocationDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutContratLocation statut) {
        return ResponseEntity.ok(contratService.search(statut, page, size).map(ContratLocationMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CONTRAT_LOCATION_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<ContratLocationDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(ContratLocationMapper.toDTO(contratService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.CONTRAT_LOCATION_CREER + "')")
    @Transactional
    public ResponseEntity<ContratLocationDTO> creer(@Valid @RequestBody ContratLocationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ContratLocationMapper.toDTO(contratService.creerDepuisRequest(req)));
    }

    @PostMapping("/{guid}/retourner")
    @PreAuthorize("hasAuthority('" + Permissions.CONTRAT_LOCATION_RETOURNER + "')")
    @Transactional
    public ResponseEntity<ContratLocationDTO> retourner(@PathVariable String guid) {
        return ResponseEntity.ok(ContratLocationMapper.toDTO(contratService.retourner(guid)));
    }

    @PostMapping("/{guid}/annuler")
    @PreAuthorize("hasAuthority('" + Permissions.CONTRAT_LOCATION_ANNULER + "')")
    @Transactional
    public ResponseEntity<ContratLocationDTO> annuler(@PathVariable String guid) {
        return ResponseEntity.ok(ContratLocationMapper.toDTO(contratService.annuler(guid)));
    }
}
