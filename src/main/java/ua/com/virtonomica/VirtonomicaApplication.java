package ua.com.virtonomica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableCaching
public class VirtonomicaApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(VirtonomicaApplication.class);

        builder.headless(false);
        /*ConfigurableApplicationContext context = */builder.run(args);
//        SpringApplication.run(VirtonomicaApplication.class, args);
    }

}
