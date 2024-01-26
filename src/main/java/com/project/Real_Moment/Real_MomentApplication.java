package com.project.Real_Moment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Real_MomentApplication {

    public static void main(String[] args) {
        SpringApplication.run(Real_MomentApplication.class, args);
    }
}
