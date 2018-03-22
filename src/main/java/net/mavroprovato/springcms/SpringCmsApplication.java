package net.mavroprovato.springcms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@EntityScan(
    basePackageClasses = {SpringCmsApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
public class SpringCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCmsApplication.class, args);
    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }
}
