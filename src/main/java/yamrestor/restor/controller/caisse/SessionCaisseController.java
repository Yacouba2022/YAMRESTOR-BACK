package yamrestor.restor.controller.caisse;

import yamrestor.restor.dto.caisse.SessionCaisseDTO;
import yamrestor.restor.dto.request.caisse.FermerSessionRequest;
import yamrestor.restor.dto.request.caisse.OuvrirSessionRequest;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.enums.StatutSessionCaisse;
import yamrestor.restor.mapper.caisse.SessionCaisseMapper;
import yamrestor.restor.service.caisse.SessionCaisseService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessions-caisse")
@RequiredArgsConstructor
@Tag(name = "Sessions de caisse")
@SecurityRequirement(name = "bearerAuth")
public class SessionCaisseController {

    private final SessionCaisseService sessionService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.SESSION_CAISSE_CONSULTER + "')")
    public ResponseEntity<Page<SessionCaisseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) StatutSessionCaisse statut) {
        return ResponseEntity.ok(sessionService.search(statut, page, size).map(SessionCaisseMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.SESSION_CAISSE_CONSULTER + "')")
    public ResponseEntity<SessionCaisseDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(SessionCaisseMapper.toDTO(sessionService.findByGuid(guid)));
    }

    /** Session de caisse actuellement ouverte par l'utilisateur connecté (204 si aucune). */
    @GetMapping("/ma-session")
    @PreAuthorize("hasAuthority('" + Permissions.SESSION_CAISSE_CONSULTER + "')")
    public ResponseEntity<SessionCaisseDTO> maSession(@AuthenticationPrincipal UserEntity user) {
        var s = sessionService.sessionOuverte(user.getGuid());
        return s != null ? ResponseEntity.ok(SessionCaisseMapper.toDTO(s)) : ResponseEntity.noContent().build();
    }

    @PostMapping("/ouvrir")
    @PreAuthorize("hasAuthority('" + Permissions.SESSION_CAISSE_OUVRIR + "')")
    public ResponseEntity<SessionCaisseDTO> ouvrir(@Valid @RequestBody OuvrirSessionRequest req,
                                                   @AuthenticationPrincipal UserEntity user) {
        String caissierGuid = (req.getCaissierGuid() != null && !req.getCaissierGuid().isBlank())
                ? req.getCaissierGuid() : user.getGuid();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SessionCaisseMapper.toDTO(sessionService.ouvrir(caissierGuid, req.getFondInitial())));
    }

    @PostMapping("/{guid}/fermer")
    @PreAuthorize("hasAuthority('" + Permissions.SESSION_CAISSE_FERMER + "')")
    public ResponseEntity<SessionCaisseDTO> fermer(@PathVariable String guid,
                                                   @Valid @RequestBody FermerSessionRequest req) {
        return ResponseEntity.ok(SessionCaisseMapper.toDTO(
                sessionService.fermer(guid, req.getFondFinalReel(), req.getCommentaire())));
    }
}
