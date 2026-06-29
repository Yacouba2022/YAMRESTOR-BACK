package yamrestor.restor.dto.request.traiteur;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AffectationPersonnelRequest {
    @NotBlank(message = "Le membre du personnel est obligatoire")
    private String personnelGuid;
    private String role;
}
