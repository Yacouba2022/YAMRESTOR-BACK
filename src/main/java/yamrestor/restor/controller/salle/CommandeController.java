package yamrestor.restor.controller.salle;

import yamrestor.restor.dto.salle.CommandeDTO;
import yamrestor.restor.dto.request.salle.CommandeRequest;
import yamrestor.restor.enums.StatutCommande;
import yamrestor.restor.enums.TypeCommande;
import yamrestor.restor.mapper.salle.CommandeMapper;
import yamrestor.restor.service.salle.CommandeService;
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
@RequestMapping("/api/v1/commandes")
@RequiredArgsConstructor
@Tag(name = "Commandes")
@SecurityRequirement(name = "bearerAuth")
public class CommandeController {

    private final CommandeService commandeService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_CONSULTER + "')")
    public ResponseEntity<Page<CommandeDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutCommande statut,
            @RequestParam(required = false) TypeCommande type) {
        return ResponseEntity.ok(commandeService.search(statut, type, page, size).map(CommandeMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<CommandeDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(CommandeMapper.toDTO(commandeService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_CREER + "')")
    @Transactional
    public ResponseEntity<CommandeDTO> creer(@Valid @RequestBody CommandeRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommandeMapper.toDTO(commandeService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_MODIFIER + "')")
    @Transactional
    public ResponseEntity<CommandeDTO> modifier(@PathVariable String guid,
                                                 @Valid @RequestBody CommandeRequest req) {
        return ResponseEntity.ok(CommandeMapper.toDTO(commandeService.modifierDepuisRequest(guid, req)));
    }

    /** Envoie la commande en cuisine (impression du bon cuisine côté front). */
    @PostMapping("/{guid}/envoyer")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ENVOYER + "')")
    @Transactional
    public ResponseEntity<CommandeDTO> envoyer(@PathVariable String guid) {
        return ResponseEntity.ok(CommandeMapper.toDTO(commandeService.envoyer(guid)));
    }

    @PostMapping("/{guid}/annuler")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_ANNULER + "')")
    @Transactional
    public ResponseEntity<CommandeDTO> annuler(@PathVariable String guid) {
        return ResponseEntity.ok(CommandeMapper.toDTO(commandeService.annuler(guid)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.COMMANDE_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        commandeService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
