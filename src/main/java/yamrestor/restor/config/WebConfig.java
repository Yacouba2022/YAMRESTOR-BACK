package yamrestor.restor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Paths;

/**
 * CORS géré par Spring Security (voir SecurityConfig).
 * Ce fichier expose les images uploadées en tant que ressources statiques.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.produits.dir:uploads/produits/photos}")
    private String uploadProduitsDir;

    @Value("${app.upload.users.dir:uploads/users/photos}")
    private String uploadUsersDir;

    @Value("${app.upload.restaurant.dir:uploads/restaurant}")
    private String uploadRestaurantDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Photos produits → /produits/photos/**
        registry.addResourceHandler("/produits/photos/**")
                .addResourceLocations("file:" + abs(uploadProduitsDir) + "/");

        // Photos utilisateurs → /users/photos/**
        registry.addResourceHandler("/users/photos/**")
                .addResourceLocations("file:" + abs(uploadUsersDir) + "/");

        // Logo / images du restaurant → /restaurant/**
        registry.addResourceHandler("/restaurant/**")
                .addResourceLocations("file:" + abs(uploadRestaurantDir) + "/");
    }

    private String abs(String dir) {
        return Paths.get(dir).toAbsolutePath().normalize().toString();
    }
}
