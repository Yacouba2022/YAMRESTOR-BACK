package yamrestor.restor.controller.stock;

import yamrestor.restor.dto.catalogue.MatierePremiereDTO;
import yamrestor.restor.dto.request.stock.AjustementStockRequest;
import yamrestor.restor.dto.request.stock.MouvementStockRequest;
import yamrestor.restor.dto.stock.MouvementStockDTO;
import yamrestor.restor.mapper.catalogue.MatierePremiereMapper;
import yamrestor.restor.mapper.stock.MouvementStockMapper;
import yamrestor.restor.service.stock.StockService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
@Tag(name = "Stock")
@SecurityRequirement(name = "bearerAuth")
public class StockController {

    private final StockService stockService;

    /** Historique des mouvements de stock (filtrable par matière première). */
    @GetMapping("/mouvements")
    @PreAuthorize("hasAuthority('" + Permissions.MOUVEMENT_STOCK_CONSULTER + "')")
    public ResponseEntity<Page<MouvementStockDTO>> mouvements(
            @RequestParam(required = false) String matierePremiereGuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(stockService.mouvements(matierePremiereGuid, page, size).map(MouvementStockMapper::toDTO));
    }

    /** Matières premières sous le seuil d'alerte. */
    @GetMapping("/alertes")
    @PreAuthorize("hasAuthority('" + Permissions.STOCK_CONSULTER + "')")
    public ResponseEntity<List<MatierePremiereDTO>> alertes() {
        return ResponseEntity.ok(stockService.alertes().stream().map(MatierePremiereMapper::toDTO).toList());
    }

    /** Valorisation totale du stock (somme stock × prix d'achat). */
    @GetMapping("/valorisation")
    @PreAuthorize("hasAuthority('" + Permissions.STOCK_CONSULTER + "')")
    public ResponseEntity<Map<String, BigDecimal>> valorisation() {
        return ResponseEntity.ok(Map.of("valorisation", stockService.valorisation()));
    }

    @PostMapping("/entree")
    @PreAuthorize("hasAuthority('" + Permissions.STOCK_MOUVEMENTER + "')")
    public ResponseEntity<MouvementStockDTO> entree(@Valid @RequestBody MouvementStockRequest req) {
        return ResponseEntity.ok(MouvementStockMapper.toDTO(
                stockService.entree(req.getMatierePremiereGuid(), req.getQuantite(), req.getMotif(), req.getReference())));
    }

    @PostMapping("/sortie")
    @PreAuthorize("hasAuthority('" + Permissions.STOCK_MOUVEMENTER + "')")
    public ResponseEntity<MouvementStockDTO> sortie(@Valid @RequestBody MouvementStockRequest req) {
        return ResponseEntity.ok(MouvementStockMapper.toDTO(
                stockService.sortie(req.getMatierePremiereGuid(), req.getQuantite(), req.getMotif(), req.getReference())));
    }

    @PostMapping("/ajustement")
    @PreAuthorize("hasAuthority('" + Permissions.STOCK_MOUVEMENTER + "')")
    public ResponseEntity<MouvementStockDTO> ajustement(@Valid @RequestBody AjustementStockRequest req) {
        return ResponseEntity.ok(MouvementStockMapper.toDTO(
                stockService.ajuster(req.getMatierePremiereGuid(), req.getNouveauStock(), req.getMotif())));
    }
}
