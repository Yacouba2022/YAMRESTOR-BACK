package yamrestor.restor.controller.location;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.location.MaterielLocationDTO;
import yamrestor.restor.dto.request.location.MaterielLocationRequest;
import yamrestor.restor.entity.location.MaterielLocationEntity;
import yamrestor.restor.enums.EtatMateriel;
import yamrestor.restor.mapper.location.MaterielLocationMapper;
import yamrestor.restor.service.location.MaterielLocationService;
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
@RequestMapping("/api/v1/materiels-location")
@RequiredArgsConstructor
@Tag(name = "Matériel de location")
@SecurityRequirement(name = "bearerAuth")
public class MaterielLocationController {

    private final MaterielLocationService materielService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_LOCATION_CONSULTER + "')")
    public ResponseEntity<Page<MaterielLocationDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(materielService.findAll(page, size).map(MaterielLocationMapper::toDTO));
    }

    @GetMapping("/actifs")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_LOCATION_CONSULTER + "')")
    public ResponseEntity<List<MaterielLocationDTO>> findActifs() {
        return ResponseEntity.ok(materielService.findActifs().stream()
                .map(MaterielLocationMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_LOCATION_CONSULTER + "')")
    public ResponseEntity<MaterielLocationDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(MaterielLocationMapper.toDTO(materielService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_LOCATION_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(materielService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_LOCATION_CREER + "')")
    public ResponseEntity<MaterielLocationDTO> creer(@Valid @RequestBody MaterielLocationRequest req) {
        MaterielLocationEntity m = new MaterielLocationEntity();
        applyCreation(m, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(MaterielLocationMapper.toDTO(materielService.save(m)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_LOCATION_MODIFIER + "')")
    public ResponseEntity<MaterielLocationDTO> modifier(@PathVariable String guid,
                                                         @Valid @RequestBody MaterielLocationRequest req) {
        MaterielLocationEntity m = materielService.findByGuid(guid);
        // Ajuste la disponibilité en suivant la variation du stock total.
        int ancienTotal = m.getQuantiteTotale() != null ? m.getQuantiteTotale() : 0;
        int nouveauTotal = req.getQuantiteTotale() != null ? req.getQuantiteTotale() : ancienTotal;
        int dispo = (m.getQuantiteDisponible() != null ? m.getQuantiteDisponible() : 0) + (nouveauTotal - ancienTotal);
        m.setNom(req.getNom());
        m.setCategorie(req.getCategorie());
        m.setQuantiteTotale(nouveauTotal);
        m.setQuantiteDisponible(Math.max(0, dispo));
        m.setPrixLocation(req.getPrixLocation() != null ? req.getPrixLocation() : BigDecimal.ZERO);
        m.setCaution(req.getCaution() != null ? req.getCaution() : BigDecimal.ZERO);
        if (req.getEtat() != null) m.setEtat(req.getEtat());
        m.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
        return ResponseEntity.ok(MaterielLocationMapper.toDTO(materielService.save(m)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MATERIEL_LOCATION_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        materielService.delete(guid);
        return ResponseEntity.noContent().build();
    }

    private void applyCreation(MaterielLocationEntity m, MaterielLocationRequest req) {
        int total = req.getQuantiteTotale() != null ? req.getQuantiteTotale() : 0;
        m.setNom(req.getNom());
        m.setCategorie(req.getCategorie());
        m.setQuantiteTotale(total);
        m.setQuantiteDisponible(total);
        m.setPrixLocation(req.getPrixLocation() != null ? req.getPrixLocation() : BigDecimal.ZERO);
        m.setCaution(req.getCaution() != null ? req.getCaution() : BigDecimal.ZERO);
        m.setEtat(req.getEtat() != null ? req.getEtat() : EtatMateriel.BON);
        m.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
