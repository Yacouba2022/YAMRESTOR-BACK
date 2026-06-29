package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.traiteur.PersonnelTraiteurDTO;
import yamrestor.restor.dto.request.traiteur.PersonnelTraiteurRequest;
import yamrestor.restor.entity.traiteur.PersonnelTraiteurEntity;
import yamrestor.restor.mapper.traiteur.PersonnelTraiteurMapper;
import yamrestor.restor.service.traiteur.PersonnelTraiteurService;
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
@RequestMapping("/api/v1/personnel-traiteur")
@RequiredArgsConstructor
@Tag(name = "Personnel traiteur")
@SecurityRequirement(name = "bearerAuth")
public class PersonnelTraiteurController {

    private final PersonnelTraiteurService personnelService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.PERSONNEL_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<Page<PersonnelTraiteurDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(personnelService.findAll(page, size).map(PersonnelTraiteurMapper::toDTO));
    }

    @GetMapping("/actifs")
    @PreAuthorize("hasAuthority('" + Permissions.PERSONNEL_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<List<PersonnelTraiteurDTO>> findActifs() {
        return ResponseEntity.ok(personnelService.findActifs().stream()
                .map(PersonnelTraiteurMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PERSONNEL_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<PersonnelTraiteurDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(PersonnelTraiteurMapper.toDTO(personnelService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.PERSONNEL_TRAITEUR_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(personnelService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.PERSONNEL_TRAITEUR_CREER + "')")
    public ResponseEntity<PersonnelTraiteurDTO> creer(@Valid @RequestBody PersonnelTraiteurRequest req) {
        PersonnelTraiteurEntity p = new PersonnelTraiteurEntity();
        apply(p, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(PersonnelTraiteurMapper.toDTO(personnelService.save(p)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PERSONNEL_TRAITEUR_MODIFIER + "')")
    public ResponseEntity<PersonnelTraiteurDTO> modifier(@PathVariable String guid,
                                                          @Valid @RequestBody PersonnelTraiteurRequest req) {
        PersonnelTraiteurEntity p = personnelService.findByGuid(guid);
        apply(p, req);
        return ResponseEntity.ok(PersonnelTraiteurMapper.toDTO(personnelService.save(p)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PERSONNEL_TRAITEUR_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        personnelService.delete(guid);
        return ResponseEntity.noContent().build();
    }

    private void apply(PersonnelTraiteurEntity p, PersonnelTraiteurRequest req) {
        p.setNom(req.getNom());
        p.setFonction(req.getFonction());
        p.setTelephone(req.getTelephone());
        p.setCoutJournalier(req.getCoutJournalier() != null ? req.getCoutJournalier() : BigDecimal.ZERO);
        p.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
