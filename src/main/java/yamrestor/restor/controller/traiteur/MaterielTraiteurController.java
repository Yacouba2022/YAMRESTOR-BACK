package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.traiteur.MaterielTraiteurDTO;
import yamrestor.restor.dto.request.traiteur.MaterielTraiteurRequest;
import yamrestor.restor.enums.EtatMateriel;
import yamrestor.restor.mapper.traiteur.MaterielTraiteurMapper;
import yamrestor.restor.service.traiteur.MaterielTraiteurService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/v1/materiel-traiteur")
@RequiredArgsConstructor
@Tag(name = "Matériel traiteur")
@SecurityRequirement(name = "bearerAuth")
public class MaterielTraiteurController {

    private final MaterielTraiteurService materielService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<Page<MaterielTraiteurDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(materielService.findAll(page, size).map(MaterielTraiteurMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<MaterielTraiteurDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(MaterielTraiteurMapper.toDTO(materielService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(materielService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_TRAITEUR_CREER + "')")
    public ResponseEntity<MaterielTraiteurDTO> creer(@Valid @RequestBody MaterielTraiteurRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MaterielTraiteurMapper.toDTO(materielService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_TRAITEUR_MODIFIER + "')")
    public ResponseEntity<MaterielTraiteurDTO> modifier(@PathVariable String guid,
                                                         @Valid @RequestBody MaterielTraiteurRequest req) {
        return ResponseEntity.ok(MaterielTraiteurMapper.toDTO(materielService.modifierDepuisRequest(guid, req)));
    }

    /** Signale une perte / casse (retire la quantité du parc). */
    @PostMapping("/{guid}/signaler")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_TRAITEUR_MODIFIER + "')")
    public ResponseEntity<MaterielTraiteurDTO> signaler(@PathVariable String guid,
                                                         @RequestBody Map<String, Integer> body) {
        int quantite = body.getOrDefault("quantite", 0);
        return ResponseEntity.ok(MaterielTraiteurMapper.toDTO(materielService.signalerPerteCasse(guid, quantite)));
    }

    /** Met à jour l'état du matériel (entretien). */
    @PatchMapping("/{guid}/etat")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_TRAITEUR_MODIFIER + "')")
    public ResponseEntity<MaterielTraiteurDTO> changerEtat(@PathVariable String guid,
                                                            @RequestBody Map<String, String> body) {
        EtatMateriel etat = EtatMateriel.valueOf(body.get("etat"));
        return ResponseEntity.ok(MaterielTraiteurMapper.toDTO(materielService.changerEtat(guid, etat)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_TRAITEUR_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        materielService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
