package yamrestor.restor.controller.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.catalogue.CategorieDTO;
import yamrestor.restor.dto.request.catalogue.CategorieRequest;
import yamrestor.restor.entity.catalogue.CategorieEntity;
import yamrestor.restor.mapper.catalogue.CategorieMapper;
import yamrestor.restor.service.catalogue.CategorieService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Catégories")
@SecurityRequirement(name = "bearerAuth")
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_CONSULTER + "')")
    public ResponseEntity<Page<CategorieDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(categorieService.findAll(page, size).map(CategorieMapper::toDTO));
    }

    @GetMapping("/actives")
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_CONSULTER + "')")
    public ResponseEntity<List<CategorieDTO>> findActives() {
        return ResponseEntity.ok(categorieService.findActives().stream()
                .map(CategorieMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_CONSULTER + "')")
    public ResponseEntity<CategorieDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(CategorieMapper.toDTO(categorieService.findByGuid(guid)));
    }

    /** Autocomplete pour les selects (7 résultats par défaut, recherche via ?q=). */
    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(categorieService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_CREER + "')")
    public ResponseEntity<CategorieDTO> creer(@Valid @RequestBody CategorieRequest req) {
        CategorieEntity c = new CategorieEntity();
        c.setNom(req.getNom()); c.setDescription(req.getDescription());
        c.setCode(req.getCode()); c.setActif(req.getActif());
        return ResponseEntity.status(HttpStatus.CREATED).body(CategorieMapper.toDTO(categorieService.save(c)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_MODIFIER + "')")
    public ResponseEntity<CategorieDTO> modifier(@PathVariable String guid,
                                                  @Valid @RequestBody CategorieRequest req) {
        CategorieEntity c = categorieService.findByGuid(guid);
        c.setNom(req.getNom()); c.setDescription(req.getDescription());
        c.setCode(req.getCode()); c.setActif(req.getActif());
        return ResponseEntity.ok(CategorieMapper.toDTO(categorieService.save(c)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        categorieService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
