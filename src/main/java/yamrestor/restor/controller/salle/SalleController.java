package yamrestor.restor.controller.salle;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.salle.SalleDTO;
import yamrestor.restor.dto.request.salle.SalleRequest;
import yamrestor.restor.entity.salle.SalleEntity;
import yamrestor.restor.mapper.salle.SalleMapper;
import yamrestor.restor.service.salle.SalleService;
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
@RequestMapping("/api/v1/salles")
@RequiredArgsConstructor
@Tag(name = "Salles")
@SecurityRequirement(name = "bearerAuth")
public class SalleController {

    private final SalleService salleService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.SALLE_CONSULTER + "')")
    public ResponseEntity<Page<SalleDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(salleService.findAll(page, size).map(SalleMapper::toDTO));
    }

    @GetMapping("/actives")
    @PreAuthorize("hasAuthority('" + Permissions.SALLE_CONSULTER + "')")
    public ResponseEntity<List<SalleDTO>> findActives() {
        return ResponseEntity.ok(salleService.findActives().stream()
                .map(SalleMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.SALLE_CONSULTER + "')")
    public ResponseEntity<SalleDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(SalleMapper.toDTO(salleService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.SALLE_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(salleService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.SALLE_CREER + "')")
    public ResponseEntity<SalleDTO> creer(@Valid @RequestBody SalleRequest req) {
        SalleEntity s = new SalleEntity();
        s.setNom(req.getNom()); s.setDescription(req.getDescription());
        s.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
        return ResponseEntity.status(HttpStatus.CREATED).body(SalleMapper.toDTO(salleService.save(s)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.SALLE_MODIFIER + "')")
    public ResponseEntity<SalleDTO> modifier(@PathVariable String guid, @Valid @RequestBody SalleRequest req) {
        SalleEntity s = salleService.findByGuid(guid);
        s.setNom(req.getNom()); s.setDescription(req.getDescription());
        s.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
        return ResponseEntity.ok(SalleMapper.toDTO(salleService.save(s)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.SALLE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        salleService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
