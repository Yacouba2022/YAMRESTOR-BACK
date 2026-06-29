package yamrestor.restor.controller.approvisionnement;

import yamrestor.restor.dto.approvisionnement.PaiementAchatDTO;
import yamrestor.restor.dto.request.approvisionnement.PaiementAchatRequest;
import yamrestor.restor.enums.StatutPaiement;
import yamrestor.restor.mapper.approvisionnement.PaiementAchatMapper;
import yamrestor.restor.service.approvisionnement.PaiementAchatService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/paiements-achat")
@RequiredArgsConstructor
@Tag(name = "Paiements fournisseur")
@SecurityRequirement(name = "bearerAuth")
public class PaiementAchatController {

    private final PaiementAchatService paiementAchatService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_ACHAT_CONSULTER + "')")
    public ResponseEntity<Page<PaiementAchatDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutPaiement statut) {
        return ResponseEntity.ok(paiementAchatService.search(statut, page, size).map(PaiementAchatMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_ACHAT_CONSULTER + "')")
    public ResponseEntity<PaiementAchatDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(PaiementAchatMapper.toDTO(paiementAchatService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_ACHAT_CREER + "')")
    public ResponseEntity<PaiementAchatDTO> creer(@Valid @RequestBody PaiementAchatRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PaiementAchatMapper.toDTO(paiementAchatService.creer(req)));
    }

    @PostMapping("/{guid}/annuler")
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_ACHAT_ANNULER + "')")
    public ResponseEntity<PaiementAchatDTO> annuler(@PathVariable String guid) {
        return ResponseEntity.ok(PaiementAchatMapper.toDTO(paiementAchatService.annuler(guid)));
    }
}
