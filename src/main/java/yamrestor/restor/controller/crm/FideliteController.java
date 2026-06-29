package yamrestor.restor.controller.crm;

import yamrestor.restor.dto.crm.BonAchatDTO;
import yamrestor.restor.dto.crm.ClientDTO;
import yamrestor.restor.dto.request.crm.PointsRequest;
import yamrestor.restor.mapper.crm.BonAchatMapper;
import yamrestor.restor.mapper.crm.ClientMapper;
import yamrestor.restor.service.crm.FideliteService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fidelite")
@RequiredArgsConstructor
@Tag(name = "Fidélité")
@SecurityRequirement(name = "bearerAuth")
public class FideliteController {

    private final FideliteService fideliteService;

    @PostMapping("/clients/{clientGuid}/ajouter-points")
    @PreAuthorize("hasAuthority('" + Permissions.FIDELITE_GERER + "')")
    public ResponseEntity<ClientDTO> ajouterPoints(@PathVariable String clientGuid,
                                                    @Valid @RequestBody PointsRequest req) {
        return ResponseEntity.ok(ClientMapper.toDTO(fideliteService.ajouterPoints(clientGuid, req.getPoints())));
    }

    @PostMapping("/clients/{clientGuid}/retirer-points")
    @PreAuthorize("hasAuthority('" + Permissions.FIDELITE_GERER + "')")
    public ResponseEntity<ClientDTO> retirerPoints(@PathVariable String clientGuid,
                                                    @Valid @RequestBody PointsRequest req) {
        return ResponseEntity.ok(ClientMapper.toDTO(fideliteService.retirerPoints(clientGuid, req.getPoints())));
    }

    /** Convertit des points en bon d'achat. */
    @PostMapping("/clients/{clientGuid}/convertir")
    @PreAuthorize("hasAuthority('" + Permissions.FIDELITE_GERER + "')")
    public ResponseEntity<BonAchatDTO> convertir(@PathVariable String clientGuid,
                                                  @Valid @RequestBody PointsRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BonAchatMapper.toDTO(fideliteService.convertirEnBon(clientGuid, req.getPoints())));
    }

    @GetMapping("/clients/{clientGuid}/bons")
    @PreAuthorize("hasAuthority('" + Permissions.FIDELITE_CONSULTER + "')")
    public ResponseEntity<List<BonAchatDTO>> bonsClient(@PathVariable String clientGuid) {
        return ResponseEntity.ok(fideliteService.bonsClient(clientGuid).stream().map(BonAchatMapper::toDTO).toList());
    }

    @PostMapping("/bons/{bonGuid}/utiliser")
    @PreAuthorize("hasAuthority('" + Permissions.FIDELITE_GERER + "')")
    public ResponseEntity<BonAchatDTO> utiliserBon(@PathVariable String bonGuid) {
        return ResponseEntity.ok(BonAchatMapper.toDTO(fideliteService.utiliserBon(bonGuid)));
    }
}
