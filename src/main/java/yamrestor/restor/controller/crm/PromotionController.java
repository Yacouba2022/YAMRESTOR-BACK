package yamrestor.restor.controller.crm;

import yamrestor.restor.dto.crm.PromotionDTO;
import yamrestor.restor.dto.request.crm.PromotionRequest;
import yamrestor.restor.mapper.crm.PromotionMapper;
import yamrestor.restor.service.crm.PromotionService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
@Tag(name = "Promotions")
@SecurityRequirement(name = "bearerAuth")
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.PROMOTION_CONSULTER + "')")
    public ResponseEntity<Page<PromotionDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        LocalDate jour = LocalDate.now();
        LocalTime heure = LocalTime.now();
        return ResponseEntity.ok(promotionService.findAll(page, size).map(p -> PromotionMapper.toDTO(p, jour, heure)));
    }

    /** Promotions actuellement applicables. */
    @GetMapping("/actives")
    @PreAuthorize("hasAuthority('" + Permissions.PROMOTION_CONSULTER + "')")
    public ResponseEntity<List<PromotionDTO>> actives() {
        LocalDate jour = LocalDate.now();
        LocalTime heure = LocalTime.now();
        return ResponseEntity.ok(promotionService.actives().stream()
                .map(p -> PromotionMapper.toDTO(p, jour, heure)).toList());
    }

    /** Validation d'un code coupon. */
    @GetMapping("/code/{code}")
    @PreAuthorize("hasAuthority('" + Permissions.PROMOTION_CONSULTER + "')")
    public ResponseEntity<PromotionDTO> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(PromotionMapper.toDTO(promotionService.findByCode(code), LocalDate.now(), LocalTime.now()));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PROMOTION_CONSULTER + "')")
    public ResponseEntity<PromotionDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(PromotionMapper.toDTO(promotionService.findByGuid(guid), LocalDate.now(), LocalTime.now()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.PROMOTION_CREER + "')")
    public ResponseEntity<PromotionDTO> creer(@Valid @RequestBody PromotionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PromotionMapper.toDTO(promotionService.creerDepuisRequest(req), LocalDate.now(), LocalTime.now()));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PROMOTION_MODIFIER + "')")
    public ResponseEntity<PromotionDTO> modifier(@PathVariable String guid, @Valid @RequestBody PromotionRequest req) {
        return ResponseEntity.ok(PromotionMapper.toDTO(promotionService.modifierDepuisRequest(guid, req), LocalDate.now(), LocalTime.now()));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.PROMOTION_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        promotionService.delete(guid);
        return ResponseEntity.noContent().build();
    }
}
