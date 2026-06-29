package yamrestor.restor.controller.dashboard;

import yamrestor.restor.dto.dashboard.DashboardDTO;
import yamrestor.restor.service.dashboard.DashboardService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Tableau de bord")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.DASHBOARD_CONSULTER + "')")
    public ResponseEntity<DashboardDTO> synthese() {
        return ResponseEntity.ok(dashboardService.synthese());
    }
}
