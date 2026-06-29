package yamrestor.restor.controller.salle;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.salle.TableDTO;
import yamrestor.restor.dto.request.salle.TableRequest;
import yamrestor.restor.enums.StatutTable;
import yamrestor.restor.mapper.salle.TableMapper;
import yamrestor.restor.service.salle.TableService;
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
@RequestMapping("/api/v1/tables")
@RequiredArgsConstructor
@Tag(name = "Tables")
@SecurityRequirement(name = "bearerAuth")
public class TableController {

    private final TableService tableService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.TABLE_CONSULTER + "')")
    public ResponseEntity<Page<TableDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String salleGuid,
            @RequestParam(required = false) StatutTable statut) {
        return ResponseEntity.ok(tableService.search(salleGuid, statut, page, size).map(TableMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.TABLE_CONSULTER + "')")
    public ResponseEntity<TableDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(TableMapper.toDTO(tableService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.TABLE_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(tableService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.TABLE_CREER + "')")
    public ResponseEntity<TableDTO> creer(@Valid @RequestBody TableRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TableMapper.toDTO(tableService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.TABLE_MODIFIER + "')")
    public ResponseEntity<TableDTO> modifier(@PathVariable String guid, @Valid @RequestBody TableRequest req) {
        return ResponseEntity.ok(TableMapper.toDTO(tableService.modifierDepuisRequest(guid, req)));
    }

    /** Change le statut d'occupation d'une table (libre / occupée / réservée / en nettoyage). */
    @PatchMapping("/{guid}/statut")
    @PreAuthorize("hasAuthority('" + Permissions.TABLE_CHANGER_STATUT + "')")
    public ResponseEntity<TableDTO> changerStatut(@PathVariable String guid,
                                                   @RequestBody Map<String, String> body) {
        StatutTable statut = StatutTable.valueOf(body.get("statut"));
        return ResponseEntity.ok(TableMapper.toDTO(tableService.changerStatut(guid, statut)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.TABLE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        tableService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
