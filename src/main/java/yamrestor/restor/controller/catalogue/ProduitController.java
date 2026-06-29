package yamrestor.restor.controller.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.catalogue.ProduitDTO;
import yamrestor.restor.dto.request.catalogue.ProduitRequest;
import yamrestor.restor.enums.TypeProduit;
import yamrestor.restor.mapper.catalogue.ProduitMapper;
import yamrestor.restor.service.catalogue.ProduitService;
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
@RequestMapping("/api/v1/produits")
@RequiredArgsConstructor
@Tag(name = "Produits")
@SecurityRequirement(name = "bearerAuth")
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.PRODUIT_CONSULTER + "')")
    public ResponseEntity<Page<ProduitDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false) String categorieGuid,
            @RequestParam(required = false) TypeProduit type,
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) Boolean actif) {
        return ResponseEntity.ok(
                produitService.search(q, categorieGuid, type, disponible, actif, page, size)
                        .map(ProduitMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PRODUIT_CONSULTER + "')")
    public ResponseEntity<ProduitDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(ProduitMapper.toDTO(produitService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.PRODUIT_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(produitService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.PRODUIT_CREER + "')")
    public ResponseEntity<ProduitDTO> creer(@Valid @RequestBody ProduitRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProduitMapper.toDTO(produitService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PRODUIT_MODIFIER + "')")
    public ResponseEntity<ProduitDTO> modifier(@PathVariable String guid,
                                                @Valid @RequestBody ProduitRequest req) {
        return ResponseEntity.ok(ProduitMapper.toDTO(produitService.modifierDepuisRequest(guid, req)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PRODUIT_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        produitService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
