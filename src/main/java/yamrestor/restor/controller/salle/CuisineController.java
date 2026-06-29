package yamrestor.restor.controller.salle;

import yamrestor.restor.dto.salle.KdsLigneDTO;
import yamrestor.restor.enums.StatutPreparation;
import yamrestor.restor.mapper.salle.KdsMapper;
import yamrestor.restor.service.salle.CuisineService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cuisine")
@RequiredArgsConstructor
@Tag(name = "Cuisine (KDS)")
@SecurityRequirement(name = "bearerAuth")
public class CuisineController {

    private final CuisineService cuisineService;

    /** Écran cuisine : lignes des commandes envoyées, filtrables par statut de préparation. */
    @GetMapping("/lignes")
    @PreAuthorize("hasAuthority('" + Permissions.CUISINE_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<List<KdsLigneDTO>> lignes(@RequestParam(required = false) StatutPreparation statut) {
        return ResponseEntity.ok(cuisineService.lignes(statut).stream().map(KdsMapper::toDTO).toList());
    }

    /** Fait avancer une ligne à l'étape suivante de préparation. */
    @PostMapping("/lignes/{ligneGuid}/avancer")
    @PreAuthorize("hasAuthority('" + Permissions.CUISINE_CHANGER_STATUT + "')")
    @Transactional
    public ResponseEntity<KdsLigneDTO> avancer(@PathVariable String ligneGuid) {
        return ResponseEntity.ok(KdsMapper.toDTO(cuisineService.avancer(ligneGuid)));
    }

    /** Force le statut de préparation d'une ligne. */
    @PatchMapping("/lignes/{ligneGuid}/statut")
    @PreAuthorize("hasAuthority('" + Permissions.CUISINE_CHANGER_STATUT + "')")
    @Transactional
    public ResponseEntity<KdsLigneDTO> changerStatut(@PathVariable String ligneGuid,
                                                      @RequestBody Map<String, String> body) {
        StatutPreparation statut = StatutPreparation.valueOf(body.get("statut"));
        return ResponseEntity.ok(KdsMapper.toDTO(cuisineService.changerStatut(ligneGuid, statut)));
    }
}
