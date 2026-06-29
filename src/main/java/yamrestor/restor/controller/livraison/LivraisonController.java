package yamrestor.restor.controller.livraison;

import yamrestor.restor.dto.livraison.LivraisonDTO;
import yamrestor.restor.dto.request.livraison.LivraisonRequest;
import yamrestor.restor.enums.StatutLivraison;
import yamrestor.restor.mapper.livraison.LivraisonMapper;
import yamrestor.restor.service.livraison.LivraisonService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/livraisons")
@RequiredArgsConstructor
@Tag(name = "Livraisons")
@SecurityRequirement(name = "bearerAuth")
public class LivraisonController {

    private final LivraisonService livraisonService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.LIVRAISON_CONSULTER + "')")
    public ResponseEntity<Page<LivraisonDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutLivraison statut) {
        return ResponseEntity.ok(livraisonService.search(statut, page, size).map(LivraisonMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.LIVRAISON_CONSULTER + "')")
    public ResponseEntity<LivraisonDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(LivraisonMapper.toDTO(livraisonService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.LIVRAISON_CREER + "')")
    public ResponseEntity<LivraisonDTO> creer(@Valid @RequestBody LivraisonRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LivraisonMapper.toDTO(livraisonService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.LIVRAISON_MODIFIER + "')")
    public ResponseEntity<LivraisonDTO> modifier(@PathVariable String guid,
                                                  @Valid @RequestBody LivraisonRequest req) {
        return ResponseEntity.ok(LivraisonMapper.toDTO(livraisonService.modifierDepuisRequest(guid, req)));
    }

    /** Change le statut de la livraison (en attente / en cours / livrée / annulée). */
    @PatchMapping("/{guid}/statut")
    @PreAuthorize("hasAuthority('" + Permissions.LIVRAISON_CHANGER_STATUT + "')")
    public ResponseEntity<LivraisonDTO> changerStatut(@PathVariable String guid,
                                                       @RequestBody Map<String, String> body) {
        StatutLivraison statut = StatutLivraison.valueOf(body.get("statut"));
        return ResponseEntity.ok(LivraisonMapper.toDTO(livraisonService.changerStatut(guid, statut)));
    }
}
