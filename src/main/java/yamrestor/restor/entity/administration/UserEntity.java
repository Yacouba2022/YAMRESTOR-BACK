package yamrestor.restor.entity.administration;

import yamrestor.restor.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class UserEntity extends BaseEntity implements UserDetails {

    // ─── Identité ────────────────────────────────────────────────────────────

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    // ─── Informations professionnelles ───────────────────────────────────────

    private String fonction;
    private String telephone;
    private String adresse;

    /** Numéro de compte interne (ex. badge, matricule) */
    private String compte;

    /** Chemin vers la photo de profil stockée sur le serveur */
    private String profilePhotoPath;

    /** Préférences d'affichage (thème, menu, etc.) sérialisées en JSON, propres à l'utilisateur. */
    @Column(columnDefinition = "TEXT")
    private String preferences;

    // ─── État du compte ───────────────────────────────────────────────────────

    /** Valeurs possibles : "actif" | "inactif" */
    @Column(nullable = false)
    private String etat = "actif";

    /**
     * Tout jeton émis AVANT cet instant est considéré invalide (déconnexion forcée par un admin).
     * null = aucune déconnexion forcée. Comparé au claim "iat" du JWT dans {@code JwtAuthFilter}.
     */
    @Column(name = "tokens_valid_after")
    private LocalDateTime tokensValidAfter;

    // ─── Relations ────────────────────────────────────────────────────────────

    /** Profil de l'utilisateur (détermine ses permissions). EAGER pour Spring Security. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profil_id")
    private ProfilEntity profil;

    // ─── Implémentation UserDetails (Spring Security) ─────────────────────────

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (profil == null || profil.getPermissions() == null) {
            return Collections.emptyList();
        }
        return profil.getPermissions().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    /** L'email est utilisé comme identifiant de connexion */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** Compte bloqué si l'état est "inactif" */
    @Override
    public boolean isAccountNonLocked() {
        return !"inactif".equals(etat);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** Compte actif uniquement si l'état vaut "actif" */
    @Override
    public boolean isEnabled() {
        return "actif".equals(etat);
    }

    // ─── Méthodes utilitaires ─────────────────────────────────────────────────

    /** Vérifie si l'utilisateur possède une permission précise */
    public boolean hasPermission(String permission) {
        if (profil == null) return false;
        return profil.getPermissions().contains(permission);
    }

    /** Code technique du rôle super administrateur (accès total). */
    public static final String SUPER_ADMIN_CODE = "super_admin";

    /** Vrai si l'utilisateur possède le rôle super administrateur (via son profil). */
    @JsonIgnore
    public boolean isSuperAdmin() {
        if (profil == null || profil.getRoles() == null) return false;
        return profil.getRoles().stream()
                .anyMatch(r -> SUPER_ADMIN_CODE.equalsIgnoreCase(r.getCode()));
    }
}
