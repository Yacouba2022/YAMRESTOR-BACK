package yamrestor.restor.controller.rapport;

import yamrestor.restor.dto.rapport.ProduitVenduDTO;
import yamrestor.restor.dto.rapport.VenteCategorieDTO;
import yamrestor.restor.dto.rapport.VentesRapportDTO;
import yamrestor.restor.service.rapport.RapportService;
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
@RequestMapping("/api/v1/rapports")
@RequiredArgsConstructor
@Tag(name = "Rapports")
@SecurityRequirement(name = "bearerAuth")
public class RapportController {

    private final RapportService rapportService;

    /** Synthèse des ventes (nb commandes, CA, TVA) sur une période. */
    @GetMapping("/ventes")
    @PreAuthorize("hasAuthority('" + Permissions.RAPPORT_CONSULTER + "')")
    public ResponseEntity<VentesRapportDTO> ventes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.ventes(debut, fin));
    }

    /** Produits les plus vendus sur une période. */
    @GetMapping("/produits-vendus")
    @PreAuthorize("hasAuthority('" + Permissions.RAPPORT_CONSULTER + "')")
    public ResponseEntity<List<ProduitVenduDTO>> produitsVendus(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(rapportService.topProduits(debut, fin, limit));
    }

    /** Ventes ventilées par catégorie sur une période. */
    @GetMapping("/ventes-par-categorie")
    @PreAuthorize("hasAuthority('" + Permissions.RAPPORT_CONSULTER + "')")
    public ResponseEntity<List<VenteCategorieDTO>> ventesParCategorie(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.ventesParCategorie(debut, fin));
    }
}
