package yamrestor.restor.controller.evenement;

import yamrestor.restor.dto.evenement.CalendrierItemDTO;
import yamrestor.restor.dto.location.MaterielLocationDTO;
import yamrestor.restor.mapper.location.MaterielLocationMapper;
import yamrestor.restor.service.evenement.EvenementService;
import yamrestor.restor.service.location.MaterielLocationService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/evenements")
@RequiredArgsConstructor
@Tag(name = "Évènements")
@SecurityRequirement(name = "bearerAuth")
public class EvenementController {

    private final EvenementService evenementService;
    private final MaterielLocationService materielService;

    /** Calendrier unifié (réservations + prestations + locations) sur une période. */
    @GetMapping("/calendrier")
    @PreAuthorize("hasAuthority('" + Permissions.EVENEMENT_CONSULTER + "')")
    public ResponseEntity<List<CalendrierItemDTO>> calendrier(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(evenementService.calendrier(debut, fin));
    }

    /** Disponibilité du matériel de location (quantités disponibles). */
    @GetMapping("/disponibilite-materiel")
    @PreAuthorize("hasAuthority('" + Permissions.EVENEMENT_CONSULTER + "')")
    public ResponseEntity<List<MaterielLocationDTO>> disponibiliteMateriel() {
        return ResponseEntity.ok(materielService.findActifs().stream()
                .map(MaterielLocationMapper::toDTO).toList());
    }
}
