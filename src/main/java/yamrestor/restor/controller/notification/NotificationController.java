package yamrestor.restor.controller.notification;

import yamrestor.restor.dto.notification.NotificationDTO;
import yamrestor.restor.service.notification.NotificationService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.NOTIFICATION_CONSULTER + "')")
    public ResponseEntity<List<NotificationDTO>> actuelles() {
        return ResponseEntity.ok(notificationService.actuelles());
    }
}
