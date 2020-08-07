package nifori.me.nifobot.orm;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "nifori.me.nifobot.orm.repositories")
public class PersistenceConfig {
    
}