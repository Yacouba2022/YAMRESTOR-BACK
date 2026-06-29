package yamrestor.restor.controller.comptabilite;

import yamrestor.restor.dto.comptabilite.DepenseDTO;
import yamrestor.restor.dto.request.comptabilite.DepenseRequest;
import yamrestor.restor.mapper.comptabilite.DepenseMapper;
import yamrestor.restor.service.comptabilite.DepenseService;
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

@RestController
@RequestMapping("/api/v1/depenses")
@RequiredArgsConstructor
@Tag(name = "Dépenses")
@SecurityRequirement(name = "bearerAuth")
public class DepenseController {

    private final DepenseService depenseService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.DEPENSE_CONSULTER + "')")
    public ResponseEntity<Page<DepenseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @RequestParam(required = false) String categorieGuid) {
        return ResponseEntity.ok(depenseService.search(debut, fin, categorieGuid, page, size).map(DepenseMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.DEPENSE_CONSULTER + "')")
    public ResponseEntity<DepenseDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(DepenseMapper.toDTO(depenseService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.DEPENSE_CREER + "')")
    public ResponseEntity<DepenseDTO> creer(@Valid @RequestBody DepenseRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DepenseMapper.toDTO(depenseService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.DEPENSE_MODIFIER + "')")
    public ResponseEntity<DepenseDTO> modifier(@PathVariable String guid, @Valid @RequestBody DepenseRequest req) {
        return ResponseEntity.ok(DepenseMapper.toDTO(depenseService.modifierDepuisRequest(guid, req)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.DEPENSE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        depenseService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
