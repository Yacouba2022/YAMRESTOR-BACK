package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.traiteur.MenuTraiteurDTO;
import yamrestor.restor.dto.request.traiteur.MenuTraiteurRequest;
import yamrestor.restor.entity.traiteur.MenuTraiteurEntity;
import yamrestor.restor.enums.TypeMenuTraiteur;
import yamrestor.restor.mapper.traiteur.MenuTraiteurMapper;
import yamrestor.restor.service.traiteur.MenuTraiteurService;
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
@RequestMapping("/api/v1/menus-traiteur")
@RequiredArgsConstructor
@Tag(name = "Menus traiteur")
@SecurityRequirement(name = "bearerAuth")
public class MenuTraiteurController {

    private final MenuTraiteurService menuService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.MENU_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<Page<MenuTraiteurDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(menuService.findAll(page, size).map(MenuTraiteurMapper::toDTO));
    }

    @GetMapping("/actives")
    @PreAuthorize("hasAuthority('" + Permissions.MENU_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<List<MenuTraiteurDTO>> findActives() {
        return ResponseEntity.ok(menuService.findActives().stream()
                .map(MenuTraiteurMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MENU_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<MenuTraiteurDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(MenuTraiteurMapper.toDTO(menuService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.MENU_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(menuService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.MENU_TRAITEUR_CREER + "')")
    public ResponseEntity<MenuTraiteurDTO> creer(@Valid @RequestBody MenuTraiteurRequest req) {
        MenuTraiteurEntity m = new MenuTraiteurEntity();
        apply(m, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(MenuTraiteurMapper.toDTO(menuService.save(m)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MENU_TRAITEUR_MODIFIER + "')")
    public ResponseEntity<MenuTraiteurDTO> modifier(@PathVariable String guid,
                                                     @Valid @RequestBody MenuTraiteurRequest req) {
        MenuTraiteurEntity m = menuService.findByGuid(guid);
        apply(m, req);
        return ResponseEntity.ok(MenuTraiteurMapper.toDTO(menuService.save(m)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MENU_TRAITEUR_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        menuService.delete(guid);
        return ResponseEntity.noContent().build();
    }

    private void apply(MenuTraiteurEntity m, MenuTraiteurRequest req) {
        m.setNom(req.getNom());
        m.setType(req.getType() != null ? req.getType() : TypeMenuTraiteur.STANDARD);
        m.setPrixParPersonne(req.getPrixParPersonne());
        m.setPrixForfaitaire(req.getPrixForfaitaire());
        m.setDescription(req.getDescription());
        m.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
