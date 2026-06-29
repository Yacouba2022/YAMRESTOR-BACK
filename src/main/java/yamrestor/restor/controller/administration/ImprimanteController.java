package yamrestor.restor.controller.administration;

import yamrestor.restor.dto.administration.ImprimanteDTO;
import yamrestor.restor.dto.request.administration.ImprimanteRequest;
import yamrestor.restor.entity.administration.ImprimanteEntity;
import yamrestor.restor.mapper.administration.ImprimanteMapper;
import yamrestor.restor.service.administration.ImprimanteService;
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
@RequestMapping("/api/v1/imprimantes")
@RequiredArgsConstructor
@Tag(name = "Imprimantes")
@SecurityRequirement(name = "bearerAuth")
public class ImprimanteController {

    private final ImprimanteService imprimanteService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.IMPRIMANTE_CONSULTER + "')")
    public ResponseEntity<Page<ImprimanteDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(imprimanteService.findAll(page, size).map(ImprimanteMapper::toDTO));
    }

    @GetMapping("/actives")
    @PreAuthorize("hasAuthority('" + Permissions.IMPRIMANTE_CONSULTER + "')")
    public ResponseEntity<List<ImprimanteDTO>> findActives() {
        return ResponseEntity.ok(imprimanteService.findActives().stream()
                .map(ImprimanteMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.IMPRIMANTE_CONSULTER + "')")
    public ResponseEntity<ImprimanteDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(ImprimanteMapper.toDTO(imprimanteService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.IMPRIMANTE_CREER + "')")
    public ResponseEntity<ImprimanteDTO> creer(@Valid @RequestBody ImprimanteRequest req) {
        ImprimanteEntity i = new ImprimanteEntity();
        apply(i, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(ImprimanteMapper.toDTO(imprimanteService.save(i)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.IMPRIMANTE_MODIFIER + "')")
    public ResponseEntity<ImprimanteDTO> modifier(@PathVariable String guid,
                                                   @Valid @RequestBody ImprimanteRequest req) {
        ImprimanteEntity i = imprimanteService.findByGuid(guid);
        apply(i, req);
        return ResponseEntity.ok(ImprimanteMapper.toDTO(imprimanteService.save(i)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.IMPRIMANTE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        imprimanteService.delete(guid);
        return ResponseEntity.noContent().build();
    }

    private void apply(ImprimanteEntity i, ImprimanteRequest req) {
        i.setNom(req.getNom());
        i.setType(req.getType());
        i.setAdresse(req.getAdresse());
        i.setModele(req.getModele());
        i.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
