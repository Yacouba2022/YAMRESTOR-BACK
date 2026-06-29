package yamrestor.restor.controller.approvisionnement;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.approvisionnement.FournisseurDTO;
import yamrestor.restor.dto.request.approvisionnement.FournisseurRequest;
import yamrestor.restor.entity.approvisionnement.FournisseurEntity;
import yamrestor.restor.mapper.approvisionnement.FournisseurMapper;
import yamrestor.restor.service.approvisionnement.FournisseurService;
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
@RequestMapping("/api/v1/fournisseurs")
@RequiredArgsConstructor
@Tag(name = "Fournisseurs")
@SecurityRequirement(name = "bearerAuth")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.FOURNISSEUR_CONSULTER + "')")
    public ResponseEntity<Page<FournisseurDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(fournisseurService.findAll(page, size).map(FournisseurMapper::toDTO));
    }

    @GetMapping("/actifs")
    @PreAuthorize("hasAuthority('" + Permissions.FOURNISSEUR_CONSULTER + "')")
    public ResponseEntity<List<FournisseurDTO>> findActifs() {
        return ResponseEntity.ok(fournisseurService.findActifs().stream()
                .map(FournisseurMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.FOURNISSEUR_CONSULTER + "')")
    public ResponseEntity<FournisseurDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(FournisseurMapper.toDTO(fournisseurService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.FOURNISSEUR_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(fournisseurService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.FOURNISSEUR_CREER + "')")
    public ResponseEntity<FournisseurDTO> creer(@Valid @RequestBody FournisseurRequest req) {
        FournisseurEntity f = new FournisseurEntity();
        apply(f, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(FournisseurMapper.toDTO(fournisseurService.save(f)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.FOURNISSEUR_MODIFIER + "')")
    public ResponseEntity<FournisseurDTO> modifier(@PathVariable String guid,
                                                    @Valid @RequestBody FournisseurRequest req) {
        FournisseurEntity f = fournisseurService.findByGuid(guid);
        apply(f, req);
        return ResponseEntity.ok(FournisseurMapper.toDTO(fournisseurService.save(f)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.FOURNISSEUR_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        fournisseurService.delete(guid);
        return ResponseEntity.noContent().build();
    }

    private void apply(FournisseurEntity f, FournisseurRequest req) {
        f.setNom(req.getNom());
        f.setTelephone(req.getTelephone());
        f.setEmail(req.getEmail());
        f.setAdresse(req.getAdresse());
        f.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
