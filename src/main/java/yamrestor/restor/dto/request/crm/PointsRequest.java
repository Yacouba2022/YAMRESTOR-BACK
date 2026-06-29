package yamrestor.restor.dto.request.crm;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/** Ajout / retrait / conversion de points de fidélité. */
@Getter @Setter
public class PointsRequest {

    @NotNull(message = "Le nombre de points est obligatoire")
    @Min(value = 1, message = "Le nombre de points doit être au moins 1")
    private Integer points;

    private String motif;
}
