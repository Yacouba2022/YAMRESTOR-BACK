package yamrestor.restor.config;

import yamrestor.restor.entity.comptabilite.CategorieDepenseEntity;
import yamrestor.restor.repository.comptabilite.CategorieDepenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/** Insère (une seule fois) les catégories de dépense usuelles d'un restaurant. */
@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class CategorieDepenseSeeder implements CommandLineRunner {

    private final CategorieDepenseRepository repository;

    private static final Map<String, String> DEFAULTS = Map.of(
            "EAU", "Eau",
            "ELEC", "Électricité",
            "GAZ", "Gaz",
            "NET", "Internet",
            "SAL", "Salaires",
            "TRANS", "Transport",
            "LOY", "Loyer",
            "DIV", "Divers"
    );

    @Override
    @Transactional
    public void run(String... args) {
        int created = 0;
        for (Map.Entry<String, String> e : DEFAULTS.entrySet()) {
            if (repository.existsByCode(e.getKey())) continue;
            CategorieDepenseEntity c = new CategorieDepenseEntity();
            c.setCode(e.getKey());
            c.setNom(e.getValue());
            c.setActif(true);
            repository.save(c);
            created++;
        }
        if (created > 0) log.info("Catégories de dépense par défaut créées : {}.", created);
    }
}
