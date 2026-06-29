package yamrestor.restor.controller.traiteur;

import yamrestor.restor.dto.request.traiteur.AffectationMaterielRequest;
import yamrestor.restor.dto.request.traiteur.AffectationPersonnelRequest;
import yamrestor.restor.dto.request.traiteur.AffectationVehiculeRequest;
import yamrestor.restor.dto.traiteur.AffectationsDTO;
import yamrestor.restor.mapper.traiteur.AffectationMapper;
import yamrestor.restor.service.traiteur.AffectationService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prestations/{prestationGuid}/affectations")
@RequiredArgsConstructor
@Tag(name = "Affectations prestation")
@SecurityRequirement(name = "bearerAuth")
public class AffectationController {

    private final AffectationService affectationService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.AFFECTATION_CONSULTER + "')")
    @Transactional(readOnly = true)
    public ResponseEntity<AffectationsDTO> affectations(@PathVariable String prestationGuid) {
        return ResponseEntity.ok(affectationService.affectations(prestationGuid));
    }

    @PostMapping("/personnel")
    @PreAuthorize("hasAuthority('" + Permissions.AFFECTATION_GERER + "')")
    public ResponseEntity<AffectationsDTO.PersonnelDTO> affecterPersonnel(
            @PathVariable String prestationGuid, @Valid @RequestBody AffectationPersonnelRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AffectationMapper.toDTO(affectationService.affecterPersonnel(prestationGuid, req)));
    }

    @PostMapping("/vehicule")
    @PreAuthorize("hasAuthority('" + Permissions.AFFECTATION_GERER + "')")
    public ResponseEntity<AffectationsDTO.VehiculeAffectDTO> affecterVehicule(
            @PathVariable String prestationGuid, @Valid @RequestBody AffectationVehiculeRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AffectationMapper.toDTO(affectationService.affecterVehicule(prestationGuid, req)));
    }

    @PostMapping("/materiel")
    @PreAuthorize("hasAuthority('" + Permissions.AFFECTATION_GERER + "')")
    public ResponseEntity<AffectationsDTO.MaterielAffectDTO> affecterMateriel(
            @PathVariable String prestationGuid, @Valid @RequestBody AffectationMaterielRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AffectationMapper.toDTO(affectationService.affecterMateriel(prestationGuid, req)));
    }

    @DeleteMapping("/personnel/{affectationGuid}")
    @PreAuthorize("hasAuthority('" + Permissions.AFFECTATION_GERER + "')")
    public ResponseEntity<Void> retirerPersonnel(@PathVariable String prestationGuid,
                                                  @PathVariable String affectationGuid) {
        affectationService.retirerPersonnel(affectationGuid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/vehicule/{affectationGuid}")
    @PreAuthorize("hasAuthority('" + Permissions.AFFECTATION_GERER + "')")
    public ResponseEntity<Void> retirerVehicule(@PathVariable String prestationGuid,
                                                 @PathVariable String affectationGuid) {
        affectationService.retirerVehicule(affectationGuid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/materiel/{affectationGuid}")
    @PreAuthorize("hasAuthority('" + Permissions.AFFECTATION_GERER + "')")
    public ResponseEntity<Void> retirerMateriel(@PathVariable String prestationGuid,
                                                 @PathVariable String affectationGuid) {
        affectationService.retirerMateriel(affectationGuid);
        return ResponseEntity.noContent().build();
    }
}
