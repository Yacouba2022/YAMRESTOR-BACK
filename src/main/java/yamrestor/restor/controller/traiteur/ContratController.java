package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.traiteur.ContratDTO;
import yamrestor.restor.enums.StatutContrat;
import yamrestor.restor.mapper.traiteur.ContratMapper;
import yamrestor.restor.service.traiteur.ContratService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats traiteur")
@SecurityRequirement(name = "bearerAuth")
public class ContratController {

    private final ContratService contratService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.CONTRAT_CONSULTER + "')")
    public ResponseEntity<Page<ContratDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutContrat statut) {
        return ResponseEntity.ok(contratService.search(statut, page, size).map(ContratMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CONTRAT_CONSULTER + "')")
    public ResponseEntity<ContratDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(ContratMapper.toDTO(contratService.findByGuid(guid)));
    }

    @PostMapping("/{guid}/signer")
    @PreAuthorize("hasAuthority('" + Permissions.CONTRAT_SIGNER + "')")
    public ResponseEntity<ContratDTO> signer(@PathVariable String guid) {
        return ResponseEntity.ok(ContratMapper.toDTO(contratService.signer(guid)));
    }

    @PostMapping("/{guid}/annuler")
    @PreAuthorize("hasAuthority('" + Permissions.CONTRAT_ANNULER + "')")
    public ResponseEntity<ContratDTO> annuler(@PathVariable String guid) {
        return ResponseEntity.ok(ContratMapper.toDTO(contratService.annuler(guid)));
    }
}
