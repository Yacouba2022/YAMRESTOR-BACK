package yamrestor.restor.controller.approvisionnement;

import yamrestor.restor.dto.approvisionnement.CommandeAchatDTO;
import yamrestor.restor.dto.request.approvisionnement.CommandeAchatRequest;
import yamrestor.restor.enums.StatutCommandeAchat;
import yamrestor.restor.mapper.approvisionnement.CommandeAchatMapper;
import yamrestor.restor.service.approvisionnement.CommandeAchatService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/commandes-achat")
@RequiredArgsConstructor
@Tag(name = "Commandes d'achat")
@SecurityRequirement(name = "bearerAuth")
public class CommandeAchatController {

    private final CommandeAchatService commandeAchatService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ACHAT_CONSULTER + "')")
    public ResponseEntity<Page<CommandeAchatDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutCommandeAchat statut) {
        return ResponseEntity.ok(commandeAchatService.search(statut, page, size).map(CommandeAchatMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ACHAT_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<CommandeAchatDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(CommandeAchatMapper.toDTO(commandeAchatService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ACHAT_CREER + "')")
    @Transactional
    public ResponseEntity<CommandeAchatDTO> creer(@Valid @RequestBody CommandeAchatRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommandeAchatMapper.toDTO(commandeAchatService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ACHAT_MODIFIER + "')")
    @Transactional
    public ResponseEntity<CommandeAchatDTO> modifier(@PathVariable String guid,
                                                      @Valid @RequestBody CommandeAchatRequest req) {
        return ResponseEntity.ok(CommandeAchatMapper.toDTO(commandeAchatService.modifierDepuisRequest(guid, req)));
    }

    @PostMapping("/{guid}/valider")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ACHAT_VALIDER + "')")
    @Transactional
    public ResponseEntity<CommandeAchatDTO> valider(@PathVariable String guid) {
        return ResponseEntity.ok(CommandeAchatMapper.toDTO(commandeAchatService.valider(guid)));
    }

    @PostMapping("/{guid}/receptionner")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ACHAT_RECEPTIONNER + "')")
    @Transactional
    public ResponseEntity<CommandeAchatDTO> receptionner(@PathVariable String guid) {
        return ResponseEntity.ok(CommandeAchatMapper.toDTO(commandeAchatService.receptionner(guid)));
    }

    @PostMapping("/{guid}/annuler")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ACHAT_ANNULER + "')")
    @Transactional
    public ResponseEntity<CommandeAchatDTO> annuler(@PathVariable String guid) {
        return ResponseEntity.ok(CommandeAchatMapper.toDTO(commandeAchatService.annuler(guid)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ACHAT_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        commandeAchatService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
