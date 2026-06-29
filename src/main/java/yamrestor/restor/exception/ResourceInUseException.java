package yamrestor.restor.exception;

/**
 * Levée lorsqu'on tente de supprimer une ressource encore référencée par
 * d'autres données (intégrité référentielle). Mappée en HTTP 409 (Conflict).
 */
public class ResourceInUseException extends RuntimeException {
    public ResourceInUseException(String message) {
        super(message);
    }
}
