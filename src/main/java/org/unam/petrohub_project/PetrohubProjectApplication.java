package org.unam.petrohub_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetrohubProjectApplication {

    public static void main(String[] args) {
        System.out.println("=== ENV DEBUG START ===");
        System.getenv().forEach((k, v) -> {
            if (k.toLowerCase().contains("jwt") || k.toLowerCase().contains("token") || k.toLowerCase().contains("db")) {
                System.out.println(k + " = " + v);
            }
        });
        System.out.println("=== ENV DEBUG END ===");

        SpringApplication.run(PetrohubProjectApplication.class, args);
    }

}
