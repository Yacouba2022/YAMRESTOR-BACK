package yamrestor.restor.config;

import yamrestor.restor.entity.administration.PermissionEntity;
import yamrestor.restor.repository.administration.PermissionRepository;
import yamrestor.restor.util.Permissions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Synchronise la table {@code permissions} avec le catalogue défini en code
 * ({@link Permissions#CATALOG}) à chaque démarrage : insère les codes manquants
 * et met à jour le libellé / module des codes existants.
 */
@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class PermissionSeeder implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void run(String... args) {
        int created = 0, updated = 0;

        for (Permissions.Def def : Permissions.CATALOG) {
            PermissionEntity p = permissionRepository.findByCode(def.code()).orElse(null);
            if (p == null) {
                p = new PermissionEntity();
                p.setCode(def.code());
                p.setNom(def.libelle());
                p.setDescription(def.libelle());
                p.setModule(def.module());
                permissionRepository.save(p);
                created++;
            } else if (!def.libelle().equals(p.getNom()) || !def.module().equals(p.getModule())) {
                p.setNom(def.libelle());
                p.setDescription(def.libelle());
                p.setModule(def.module());
                permissionRepository.save(p);
                updated++;
            }
        }

        if (created > 0 || updated > 0) {
            log.info("Permissions synchronisées : {} créée(s), {} mise(s) à jour ({} au catalogue).",
                    created, updated, Permissions.CATALOG.size());
        }
    }
}
