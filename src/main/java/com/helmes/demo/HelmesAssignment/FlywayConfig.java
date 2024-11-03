package com.helmes.demo.HelmesAssignment;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .cleanDisabled(false)  // Allow clean operation
                .dataSource("jdbc:h2:~/H2db/HelmesDb;AUTO_SERVER=TRUE;MODE=MySQL", "sa", "")
                .load();
        flyway.clean();
        flyway.migrate();
        return flyway;
    }
}
