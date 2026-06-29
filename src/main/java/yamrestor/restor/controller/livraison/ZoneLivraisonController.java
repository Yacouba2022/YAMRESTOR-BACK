package yamrestor.restor.controller.livraison;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.livraison.ZoneLivraisonDTO;
import yamrestor.restor.dto.request.livraison.ZoneLivraisonRequest;
import yamrestor.restor.entity.livraison.ZoneLivraisonEntity;
import yamrestor.restor.mapper.livraison.ZoneLivraisonMapper;
import yamrestor.restor.service.livraison.ZoneLivraisonService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/zones-livraison")
@RequiredArgsConstructor
@Tag(name = "Zones de livraison")
@SecurityRequirement(name = "bearerAuth")
public class ZoneLivraisonController {

    private final ZoneLivraisonService zoneService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.ZONE_LIVRAISON_CONSULTER + "')")
    public ResponseEntity<Page<ZoneLivraisonDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(zoneService.findAll(page, size).map(ZoneLivraisonMapper::toDTO));
    }

    @GetMapping("/actives")
    @PreAuthorize("hasAuthority('" + Permissions.ZONE_LIVRAISON_CONSULTER + "')")
    public ResponseEntity<List<ZoneLivraisonDTO>> findActives() {
        return ResponseEntity.ok(zoneService.findActives().stream()
                .map(ZoneLivraisonMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.ZONE_LIVRAISON_CONSULTER + "')")
    public ResponseEntity<ZoneLivraisonDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(ZoneLivraisonMapper.toDTO(zoneService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.ZONE_LIVRAISON_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(zoneService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.ZONE_LIVRAISON_CREER + "')")
    public ResponseEntity<ZoneLivraisonDTO> creer(@Valid @RequestBody ZoneLivraisonRequest req) {
        ZoneLivraisonEntity z = new ZoneLivraisonEntity();
        apply(z, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(ZoneLivraisonMapper.toDTO(zoneService.save(z)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.ZONE_LIVRAISON_MODIFIER + "')")
    public ResponseEntity<ZoneLivraisonDTO> modifier(@PathVariable String guid,
                                                      @Valid @RequestBody ZoneLivraisonRequest req) {
        ZoneLivraisonEntity z = zoneService.findByGuid(guid);
        apply(z, req);
        return ResponseEntity.ok(ZoneLivraisonMapper.toDTO(zoneService.save(z)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.ZONE_LIVRAISON_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        zoneService.delete(guid);
        return ResponseEntity.noContent().build();
    }

    private void apply(ZoneLivraisonEntity z, ZoneLivraisonRequest req) {
        z.setNom(req.getNom());
        z.setFrais(req.getFrais() != null ? req.getFrais() : BigDecimal.ZERO);
        z.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
