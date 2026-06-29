package yamrestor.restor.dto;

import lombok.*;

/** Option légère pour les listes de sélection / autocomplete : { value, label }. */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SelectOptionDTO {
    /** Identifiant stable (guid) à renvoyer lors de la sélection */
    private String value;
    /** Texte affiché dans le select */
    private String label;

    public static SelectOptionDTO of(String value, String label) {
        return new SelectOptionDTO(value, label);
    }
}
