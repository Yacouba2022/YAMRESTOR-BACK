package yamrestor.restor.service.notification;

import yamrestor.restor.dto.notification.NotificationDTO;

import java.util.List;

public interface NotificationService {
    /** Notifications actuelles : stock faible, péremptions, anniversaires, réservations du jour. */
    List<NotificationDTO> actuelles();
}
