package yamrestor.restor.config;

import yamrestor.restor.entity.administration.ProfilEntity;
import yamrestor.restor.entity.administration.RoleEntity;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.repository.administration.PermissionRepository;
import yamrestor.restor.repository.administration.ProfilRepository;
import yamrestor.restor.repository.administration.RoleRepository;
import yamrestor.restor.repository.administration.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Crée (si absents) un rôle « Super Admin » regroupant TOUTES les permissions du catalogue,
 * un profil « Administrateur » portant ce rôle, et un utilisateur administrateur initial.
 *
 * <p>S'exécute après {@code PermissionSeeder} (Order 1) afin que toutes les permissions
 * soient déjà en base. Le rôle Super Admin est resynchronisé à chaque démarrage avec
 * l'ensemble des permissions existantes.</p>
 */
@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private static final String SUPER_ADMIN_CODE = "super_admin";
    private static final String PROFIL_ADMIN_NOM = "Administrateur";
    private static final String ADMIN_EMAIL      = "admin@yamrestor.com";
    private static final String ADMIN_PASSWORD   = "Admin@2024";

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final ProfilRepository profilRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        RoleEntity superAdmin = seedSuperAdminRole();
        ProfilEntity profilAdmin = seedAdminProfil(superAdmin);
        seedAdminUser(profilAdmin);
    }

    /** Rôle « Super Admin » : toujours resynchronisé avec toutes les permissions. */
    private RoleEntity seedSuperAdminRole() {
        RoleEntity role = roleRepository.findByCode(SUPER_ADMIN_CODE).orElseGet(RoleEntity::new);
        role.setNom("Super Admin");
        role.setCode(SUPER_ADMIN_CODE);
        role.setDescription("Accès total à toutes les fonctionnalités");
        role.setIsActive(true);
        role.setPermissions(new HashSet<>(permissionRepository.findAll()));
        return roleRepository.save(role);
    }

    /** Profil « Administrateur » portant le rôle Super Admin. */
    private ProfilEntity seedAdminProfil(RoleEntity superAdmin) {
        ProfilEntity profil = profilRepository.findByNom(PROFIL_ADMIN_NOM).orElseGet(ProfilEntity::new);
        profil.setNom(PROFIL_ADMIN_NOM);
        profil.setDescription("Profil disposant de tous les droits");
        profil.setIsActive(true);
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(superAdmin);
        profil.setRoles(roles);
        return profilRepository.save(profil);
    }

    /** Utilisateur administrateur initial (créé une seule fois). */
    private void seedAdminUser(ProfilEntity profilAdmin) {
        if (userRepository.existsByEmail(ADMIN_EMAIL)) {
            return;
        }
        UserEntity admin = new UserEntity();
        admin.setName("Administrateur");
        admin.setEmail(ADMIN_EMAIL);
        admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        admin.setEtat("actif");
        admin.setProfil(profilAdmin);
        userRepository.save(admin);

        log.warn("=================================================================");
        log.warn(" Compte administrateur créé : {} / {}", ADMIN_EMAIL, ADMIN_PASSWORD);
        log.warn(" >>> Pensez à modifier ce mot de passe en production ! <<<");
        log.warn("=================================================================");
    }
}
