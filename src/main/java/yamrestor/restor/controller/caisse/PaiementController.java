package yamrestor.restor.controller.caisse;

import yamrestor.restor.dto.caisse.PaiementDTO;
import yamrestor.restor.dto.caisse.PaiementSituationDTO;
import yamrestor.restor.dto.request.caisse.PaiementRequest;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.enums.StatutPaiement;
import yamrestor.restor.mapper.caisse.PaiementMapper;
import yamrestor.restor.service.caisse.PaiementService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements")
@SecurityRequirement(name = "bearerAuth")
public class PaiementController {

    private final PaiementService paiementService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_CONSULTER + "')")
    public ResponseEntity<Page<PaiementDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutPaiement statut) {
        return ResponseEntity.ok(paiementService.search(statut, page, size).map(PaiementMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<PaiementDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(PaiementMapper.toDTO(paiementService.findByGuid(guid)));
    }

    /** Situation de paiement d'une commande (montant dû, payé, reste à payer). */
    @GetMapping("/commande/{commandeGuid}")
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_CONSULTER + "')")
    public ResponseEntity<PaiementSituationDTO> situation(@PathVariable String commandeGuid) {
        return ResponseEntity.ok(paiementService.situationCommande(commandeGuid));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_CREER + "')")
    @Transactional
    public ResponseEntity<PaiementDTO> creer(@Valid @RequestBody PaiementRequest req,
                                             @AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PaiementMapper.toDTO(paiementService.creer(req, user.getGuid())));
    }

    @PostMapping("/{guid}/annuler")
    @PreAuthorize("hasAuthority('" + Permissions.PAIEMENT_ANNULER + "')")
    public ResponseEntity<PaiementDTO> annuler(@PathVariable String guid) {
        return ResponseEntity.ok(PaiementMapper.toDTO(paiementService.annuler(guid)));
    }
}
