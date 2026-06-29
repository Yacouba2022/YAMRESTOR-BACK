package yamrestor.restor.config;

import yamrestor.restor.entity.catalogue.CategorieEntity;
import yamrestor.restor.repository.catalogue.CategorieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Insère (une seule fois) les catégories de produits par défaut d'un restaurant.
 */
@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class CategorieSeeder implements CommandLineRunner {

    private final CategorieRepository categorieRepository;

    private static final Map<String, String> DEFAULTS = Map.of(
            "ENT", "Entrées",
            "PLA", "Plats",
            "DES", "Desserts",
            "BOI", "Boissons",
            "MEN", "Menus",
            "COC", "Cocktails"
    );

    @Override
    @Transactional
    public void run(String... args) {
        int created = 0;
        for (Map.Entry<String, String> e : DEFAULTS.entrySet()) {
            if (categorieRepository.existsByCode(e.getKey())) continue;
            CategorieEntity c = new CategorieEntity();
            c.setCode(e.getKey());
            c.setNom(e.getValue());
            c.setActif(true);
            categorieRepository.save(c);
            created++;
        }
        if (created > 0) {
            log.info("Catégories par défaut créées : {}.", created);
        }
    }
}
