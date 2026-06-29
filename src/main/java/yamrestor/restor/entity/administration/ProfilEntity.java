package yamrestor.restor.entity.administration;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profils")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE profils SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ProfilEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean isActive = true;

    /**
     * Rôles regroupés par ce profil.
     * EAGER car nécessaire au calcul des autorisations Spring Security.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "profil_roles",
        joinColumns        = @JoinColumn(name = "profil_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    /**
     * Permissions additionnelles accordées directement au profil, en plus de celles
     * héritées de ses rôles. EAGER car elles entrent dans le calcul des autorisations.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "profil_permissions",
        joinColumns        = @JoinColumn(name = "profil_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissionsSupplementaires = new HashSet<>();

    /**
     * Permissions effectives du profil : union des permissions de ses rôles actifs
     * ET des permissions additionnelles directement attribuées. (calculé, non persisté)
     */
    @Transient
    public Set<String> getPermissions() {
        Set<String> codes = new HashSet<>();
        if (roles != null) {
            for (RoleEntity role : roles) {
                if (Boolean.FALSE.equals(role.getIsActive()) || role.getPermissions() == null) continue;
                role.getPermissions().forEach(p -> codes.add(p.getCode()));
            }
        }
        if (permissionsSupplementaires != null) {
            permissionsSupplementaires.forEach(p -> codes.add(p.getCode()));
        }
        return codes;
    }
}
