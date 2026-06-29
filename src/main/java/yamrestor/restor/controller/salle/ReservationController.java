package yamrestor.restor.controller.salle;

import yamrestor.restor.dto.salle.ReservationDTO;
import yamrestor.restor.dto.request.salle.ReservationRequest;
import yamrestor.restor.enums.StatutReservation;
import yamrestor.restor.mapper.salle.ReservationMapper;
import yamrestor.restor.service.salle.ReservationService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations")
@SecurityRequirement(name = "bearerAuth")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.RESERVATION_CONSULTER + "')")
    public ResponseEntity<Page<ReservationDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) StatutReservation statut) {
        return ResponseEntity.ok(reservationService.search(date, statut, page, size).map(ReservationMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.RESERVATION_CONSULTER + "')")
    public ResponseEntity<ReservationDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(ReservationMapper.toDTO(reservationService.findByGuid(guid)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.RESERVATION_CREER + "')")
    public ResponseEntity<ReservationDTO> creer(@Valid @RequestBody ReservationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReservationMapper.toDTO(reservationService.creerDepuisRequest(req)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.RESERVATION_MODIFIER + "')")
    public ResponseEntity<ReservationDTO> modifier(@PathVariable String guid,
                                                    @Valid @RequestBody ReservationRequest req) {
        return ResponseEntity.ok(ReservationMapper.toDTO(reservationService.modifierDepuisRequest(guid, req)));
    }

    @PostMapping("/{guid}/confirmer")
    @PreAuthorize("hasAuthority('" + Permissions.RESERVATION_CONFIRMER + "')")
    public ResponseEntity<ReservationDTO> confirmer(@PathVariable String guid) {
        return ResponseEntity.ok(ReservationMapper.toDTO(reservationService.confirmer(guid)));
    }

    @PostMapping("/{guid}/annuler")
    @PreAuthorize("hasAuthority('" + Permissions.RESERVATION_ANNULER + "')")
    public ResponseEntity<ReservationDTO> annuler(@PathVariable String guid) {
        return ResponseEntity.ok(ReservationMapper.toDTO(reservationService.annuler(guid)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.RESERVATION_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        reservationService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
