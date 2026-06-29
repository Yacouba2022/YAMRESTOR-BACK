package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.traiteur.ContratDTO;
import yamrestor.restor.dto.traiteur.DevisDTO;
import yamrestor.restor.dto.request.traiteur.DevisRequest;
import yamrestor.restor.enums.StatutDevis;
import yamrestor.restor.mapper.traiteur.ContratMapper;
import yamrestor.restor.mapper.traiteur.DevisMapper;
import yamrestor.restor.service.traiteur.DevisService;
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
@RequestMapping("/api/v1/devis")
@RequiredArgsConstructor
@Tag(name = "Devis traiteur")
@SecurityRequirement(name = "bearerAuth")
public class DevisController {

    private final DevisService devisService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.DEVIS_CONSULTER + "')")
    public ResponseEntity<Page<DevisDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutDevis statut) {
        return ResponseEntity.ok(devisService.search(statut, page, size).map(DevisMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.DEVIS_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<DevisDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(DevisMapper.toDTO(devisService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.DEVIS_CREER + "')")
    @Transactional
    public ResponseEntity<DevisDTO> creer(@Valid @RequestBody DevisRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DevisMapper.toDTO(devisService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.DEVIS_MODIFIER + "')")
    @Transactional
    public ResponseEntity<DevisDTO> modifier(@PathVariable String guid, @Valid @RequestBody DevisRequest req) {
        return ResponseEntity.ok(DevisMapper.toDTO(devisService.modifierDepuisRequest(guid, req)));
    }

    @PostMapping("/{guid}/valider")
    @PreAuthorize("hasAuthority('" + Permissions.DEVIS_VALIDER + "')")
    @Transactional
    public ResponseEntity<DevisDTO> valider(@PathVariable String guid) {
        return ResponseEntity.ok(DevisMapper.toDTO(devisService.valider(guid)));
    }

    /** Convertit le devis en contrat (confirme la prestation). */
    @PostMapping("/{guid}/convertir")
    @PreAuthorize("hasAuthority('" + Permissions.DEVIS_CONVERTIR + "')")
    @Transactional
    public ResponseEntity<ContratDTO> convertir(@PathVariable String guid) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ContratMapper.toDTO(devisService.convertirEnContrat(guid)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.DEVIS_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        devisService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
