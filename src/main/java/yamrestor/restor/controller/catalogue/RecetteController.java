package yamrestor.restor.controller.catalogue;

import yamrestor.restor.dto.catalogue.RecetteDTO;
import yamrestor.restor.dto.request.catalogue.RecetteRequest;
import yamrestor.restor.mapper.catalogue.RecetteMapper;
import yamrestor.restor.service.catalogue.RecetteService;
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
@RequestMapping("/api/v1/recettes")
@RequiredArgsConstructor
@Tag(name = "Recettes")
@SecurityRequirement(name = "bearerAuth")
public class RecetteController {

    private final RecetteService recetteService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.RECETTE_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<RecetteDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(recetteService.findAll(page, size).map(RecetteMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.RECETTE_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<RecetteDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(RecetteMapper.toDTO(recetteService.findByGuid(guid)));
    }

    /** Recette associée à un produit. */
    @GetMapping("/produit/{produitGuid}")
    @PreAuthorize("hasAuthority('" + Permissions.RECETTE_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<RecetteDTO> findByProduit(@PathVariable String produitGuid) {
        return ResponseEntity.ok(RecetteMapper.toDTO(recetteService.findByProduit(produitGuid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.RECETTE_CREER + "')")
    @Transactional
    public ResponseEntity<RecetteDTO> creer(@Valid @RequestBody RecetteRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RecetteMapper.toDTO(recetteService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.RECETTE_MODIFIER + "')")
    @Transactional
    public ResponseEntity<RecetteDTO> modifier(@PathVariable String guid,
                                                @Valid @RequestBody RecetteRequest req) {
        return ResponseEntity.ok(RecetteMapper.toDTO(recetteService.modifierDepuisRequest(guid, req)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.RECETTE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        recetteService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
