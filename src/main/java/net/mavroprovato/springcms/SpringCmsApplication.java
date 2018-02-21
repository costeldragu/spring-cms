package net.mavroprovato.springcms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
        basePackageClasses = {SpringCmsApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
public class SpringCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCmsApplication.class, args);
    }
}
