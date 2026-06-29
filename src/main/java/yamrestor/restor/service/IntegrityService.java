package yamrestor.restor.service;

import yamrestor.restor.entity.administration.ProfilEntity;
import yamrestor.restor.entity.administration.RoleEntity;
import yamrestor.restor.entity.administration.PermissionEntity;
import yamrestor.restor.entity.catalogue.CategorieEntity;
import yamrestor.restor.exception.ResourceInUseException;
import yamrestor.restor.repository.administration.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Vérifie l'intégrité référentielle avant suppression d'une ressource :
 * lève {@link ResourceInUseException} si la ressource est encore référencée par d'autres données.
 *
 * <p>Les méthodes sont ajoutées au fur et à mesure que les modules dépendants sont implémentés.</p>
 */
@Service
@RequiredArgsConstructor
public class IntegrityService {

    private final UserRepository userRepository;

    /** Une catégorie ne peut être supprimée que si aucun produit ne la référence. */
    public void checkCategorieDeletable(CategorieEntity categorie) {
        // Vérification fine (produits référençant la catégorie) à activer si nécessaire.
    }

    /** Une unité ne peut être supprimée que si aucun produit/matière première ne la référence. */
    public void checkUniteDeletable(yamrestor.restor.entity.catalogue.UniteEntity unite) {
        // Vérification fine à activer si nécessaire.
    }

    /** Une matière première ne peut être supprimée que si aucune recette ne la référence. */
    public void checkMatiereDeletable(yamrestor.restor.entity.catalogue.MatierePremiereEntity matiere) {
        // Vérification fine à activer si nécessaire.
    }

    /** Un produit ne peut être supprimé que si aucune recette/commande ne le référence. */
    public void checkProduitDeletable(yamrestor.restor.entity.catalogue.ProduitEntity produit) {
        // Vérification fine à activer si nécessaire.
    }

    /** Un profil ne peut être supprimé que si aucun utilisateur ne lui est rattaché. */
    public void checkProfilDeletable(ProfilEntity profil) {
        long count = userRepository.countByProfil(profil);
        if (count > 0) {
            throw new ResourceInUseException(
                    "Ce profil est attribué à " + count + " utilisateur(s) et ne peut pas être supprimé.");
        }
    }

    /** Un rôle ne peut être supprimé que s'il n'est utilisé par aucun profil (vérif simplifiée pour l'instant). */
    public void checkRoleDeletable(RoleEntity role) {
        // Vérification fine à ajouter si nécessaire (profils référençant ce rôle).
    }

    /** Une permission ne peut être supprimée que si elle n'est référencée par aucun rôle/profil. */
    public void checkPermissionDeletable(PermissionEntity permission) {
        // Vérification fine à ajouter si nécessaire.
    }
}
