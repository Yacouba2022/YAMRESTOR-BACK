package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.traiteur.VehiculeDTO;
import yamrestor.restor.dto.request.traiteur.VehiculeRequest;
import yamrestor.restor.entity.traiteur.VehiculeEntity;
import yamrestor.restor.mapper.traiteur.VehiculeMapper;
import yamrestor.restor.service.traiteur.VehiculeService;
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
@RequestMapping("/api/v1/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules")
@SecurityRequirement(name = "bearerAuth")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.VEHICULE_CONSULTER + "')")
    public ResponseEntity<Page<VehiculeDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(vehiculeService.findAll(page, size).map(VehiculeMapper::toDTO));
    }

    @GetMapping("/actifs")
    @PreAuthorize("hasAuthority('" + Permissions.VEHICULE_CONSULTER + "')")
    public ResponseEntity<List<VehiculeDTO>> findActifs() {
        return ResponseEntity.ok(vehiculeService.findActifs().stream()
                .map(VehiculeMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.VEHICULE_CONSULTER + "')")
    public ResponseEntity<VehiculeDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(VehiculeMapper.toDTO(vehiculeService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.VEHICULE_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(vehiculeService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.VEHICULE_CREER + "')")
    public ResponseEntity<VehiculeDTO> creer(@Valid @RequestBody VehiculeRequest req) {
        VehiculeEntity v = new VehiculeEntity();
        apply(v, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(VehiculeMapper.toDTO(vehiculeService.save(v)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.VEHICULE_MODIFIER + "')")
    public ResponseEntity<VehiculeDTO> modifier(@PathVariable String guid, @Valid @RequestBody VehiculeRequest req) {
        VehiculeEntity v = vehiculeService.findByGuid(guid);
        apply(v, req);
        return ResponseEntity.ok(VehiculeMapper.toDTO(vehiculeService.save(v)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.VEHICULE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        vehiculeService.delete(guid);
        return ResponseEntity.noContent().build();
    }

    private void apply(VehiculeEntity v, VehiculeRequest req) {
        v.setNom(req.getNom());
        v.setImmatriculation(req.getImmatriculation());
        v.setType(req.getType());
        v.setKilometrage(req.getKilometrage());
        v.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
