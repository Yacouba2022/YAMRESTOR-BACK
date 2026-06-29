package yamrestor.restor.dto.notification;

import lombok.*;

/** Notification calculée à la volée (non persistée). */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationDTO {
    /** Catégorie : STOCK_FAIBLE, PEREMPTION, ANNIVERSAIRE, RESERVATION. */
    private String type;
    /** Niveau d'urgence : info, warning, danger. */
    private String niveau;
    private String titre;
    private String message;
}
