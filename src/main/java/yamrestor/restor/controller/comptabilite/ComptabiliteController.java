package yamrestor.restor.controller.comptabilite;

import yamrestor.restor.dto.comptabilite.CompteResultatDTO;
import yamrestor.restor.service.comptabilite.ComptabiliteService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/comptabilite")
@RequiredArgsConstructor
@Tag(name = "Comptabilité")
@SecurityRequirement(name = "bearerAuth")
public class ComptabiliteController {

    private final ComptabiliteService comptabiliteService;

    /** Compte de résultat (recettes − dépenses) sur une période ; par défaut le mois en cours. */
    @GetMapping("/compte-resultat")
    @PreAuthorize("hasAuthority('" + Permissions.COMPTABILITE_CONSULTER + "')")
    public ResponseEntity<CompteResultatDTO> compteResultat(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(comptabiliteService.compteResultat(debut, fin));
    }
}
