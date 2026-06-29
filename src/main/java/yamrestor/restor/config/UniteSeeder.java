package yamrestor.restor.config;

import yamrestor.restor.entity.catalogue.UniteEntity;
import yamrestor.restor.repository.catalogue.UniteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Insère (une seule fois) les unités de mesure usuelles d'un restaurant. */
@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class UniteSeeder implements CommandLineRunner {

    private final UniteRepository uniteRepository;

    private record U(String code, String nom, String symbole) {}

    private static final List<U> DEFAULTS = List.of(
            new U("KG", "Kilogramme", "kg"),
            new U("G", "Gramme", "g"),
            new U("L", "Litre", "L"),
            new U("CL", "Centilitre", "cl"),
            new U("PCE", "Pièce", "pce"),
            new U("PORT", "Portion", "port")
    );

    @Override
    @Transactional
    public void run(String... args) {
        int created = 0;
        for (U u : DEFAULTS) {
            if (uniteRepository.existsByCode(u.code())) continue;
            UniteEntity e = new UniteEntity();
            e.setCode(u.code());
            e.setNom(u.nom());
            e.setSymbole(u.symbole());
            e.setActif(true);
            uniteRepository.save(e);
            created++;
        }
        if (created > 0) log.info("Unités par défaut créées : {}.", created);
    }
}
