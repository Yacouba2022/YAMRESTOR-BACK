package yamrestor.restor.controller.catalogue;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.catalogue.MatierePremiereDTO;
import yamrestor.restor.dto.request.catalogue.MatierePremiereRequest;
import yamrestor.restor.mapper.catalogue.MatierePremiereMapper;
import yamrestor.restor.service.catalogue.MatierePremiereService;
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

@RestController
@RequestMapping("/api/v1/matieres-premieres")
@RequiredArgsConstructor
@Tag(name = "Matières premières")
@SecurityRequirement(name = "bearerAuth")
public class MatierePremiereController {

    private final MatierePremiereService matiereService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.MATIERE_CONSULTER + "')")
    public ResponseEntity<Page<MatierePremiereDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false) String uniteGuid,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(defaultValue = "false") boolean stockFaible) {
        return ResponseEntity.ok(
                matiereService.search(q, uniteGuid, actif, stockFaible, page, size)
                        .map(MatierePremiereMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MATIERE_CONSULTER + "')")
    public ResponseEntity<MatierePremiereDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(MatierePremiereMapper.toDTO(matiereService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.MATIERE_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(matiereService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.MATIERE_CREER + "')")
    public ResponseEntity<MatierePremiereDTO> creer(@Valid @RequestBody MatierePremiereRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MatierePremiereMapper.toDTO(matiereService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MATIERE_MODIFIER + "')")
    public ResponseEntity<MatierePremiereDTO> modifier(@PathVariable String guid,
                                                        @Valid @RequestBody MatierePremiereRequest req) {
        return ResponseEntity.ok(MatierePremiereMapper.toDTO(matiereService.modifierDepuisRequest(guid, req)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.MATIERE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        matiereService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
