package yamrestor.restor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.DIRECT;

@Configuration
@EnableJpaAuditing
@EnableSpringDataWebSupport(pageSerializationMode = DIRECT)
public class JpaConfig {
}
