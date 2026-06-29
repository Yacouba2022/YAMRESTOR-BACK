package yamrestor.restor.controller.comptabilite;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.comptabilite.CategorieDepenseDTO;
import yamrestor.restor.dto.request.comptabilite.CategorieDepenseRequest;
import yamrestor.restor.entity.comptabilite.CategorieDepenseEntity;
import yamrestor.restor.mapper.comptabilite.CategorieDepenseMapper;
import yamrestor.restor.service.comptabilite.CategorieDepenseService;
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
@RequestMapping("/api/v1/categories-depense")
@RequiredArgsConstructor
@Tag(name = "Catégories de dépense")
@SecurityRequirement(name = "bearerAuth")
public class CategorieDepenseController {

    private final CategorieDepenseService categorieService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_DEPENSE_CONSULTER + "')")
    public ResponseEntity<Page<CategorieDepenseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(categorieService.findAll(page, size).map(CategorieDepenseMapper::toDTO));
    }

    @GetMapping("/actives")
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_DEPENSE_CONSULTER + "')")
    public ResponseEntity<List<CategorieDepenseDTO>> findActives() {
        return ResponseEntity.ok(categorieService.findActives().stream()
                .map(CategorieDepenseMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_DEPENSE_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(categorieService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_DEPENSE_CREER + "')")
    public ResponseEntity<CategorieDepenseDTO> creer(@Valid @RequestBody CategorieDepenseRequest req) {
        CategorieDepenseEntity c = new CategorieDepenseEntity();
        c.setNom(req.getNom()); c.setCode(req.getCode());
        c.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategorieDepenseMapper.toDTO(categorieService.save(c)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_DEPENSE_MODIFIER + "')")
    public ResponseEntity<CategorieDepenseDTO> modifier(@PathVariable String guid,
                                                         @Valid @RequestBody CategorieDepenseRequest req) {
        CategorieDepenseEntity c = categorieService.findByGuid(guid);
        c.setNom(req.getNom()); c.setCode(req.getCode());
        c.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
        return ResponseEntity.ok(CategorieDepenseMapper.toDTO(categorieService.save(c)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CATEGORIE_DEPENSE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        categorieService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
