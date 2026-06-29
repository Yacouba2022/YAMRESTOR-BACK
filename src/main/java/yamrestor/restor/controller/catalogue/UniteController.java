package yamrestor.restor.controller.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.catalogue.UniteDTO;
import yamrestor.restor.dto.request.catalogue.UniteRequest;
import yamrestor.restor.entity.catalogue.UniteEntity;
import yamrestor.restor.mapper.catalogue.UniteMapper;
import yamrestor.restor.service.catalogue.UniteService;
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
@RequestMapping("/api/v1/unites")
@RequiredArgsConstructor
@Tag(name = "Unités")
@SecurityRequirement(name = "bearerAuth")
public class UniteController {

    private final UniteService uniteService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.UNITE_CONSULTER + "')")
    public ResponseEntity<Page<UniteDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(uniteService.findAll(page, size).map(UniteMapper::toDTO));
    }

    @GetMapping("/actives")
    @PreAuthorize("hasAuthority('" + Permissions.UNITE_CONSULTER + "')")
    public ResponseEntity<List<UniteDTO>> findActives() {
        return ResponseEntity.ok(uniteService.findActives().stream()
                .map(UniteMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.UNITE_CONSULTER + "')")
    public ResponseEntity<UniteDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(UniteMapper.toDTO(uniteService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.UNITE_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(uniteService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.UNITE_CREER + "')")
    public ResponseEntity<UniteDTO> creer(@Valid @RequestBody UniteRequest req) {
        UniteEntity u = new UniteEntity();
        apply(u, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(UniteMapper.toDTO(uniteService.save(u)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.UNITE_MODIFIER + "')")
    public ResponseEntity<UniteDTO> modifier(@PathVariable String guid, @Valid @RequestBody UniteRequest req) {
        UniteEntity u = uniteService.findByGuid(guid);
        apply(u, req);
        return ResponseEntity.ok(UniteMapper.toDTO(uniteService.save(u)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.UNITE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        uniteService.delete(guid);
        return ResponseEntity.noContent().build();
    }

    private void apply(UniteEntity u, UniteRequest req) {
        u.setNom(req.getNom());
        u.setCode(req.getCode());
        u.setSymbole(req.getSymbole());
        u.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
