package org.unam.petrohub_project;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetrohubProjectApplication {

    public static void main(String[] args) {
/*        Dotenv dotenv = Dotenv.load();
        System.setProperty("TOKEN_DURATION", dotenv.get("TOKEN_DURATION"));
        System.setProperty("TOKEN_EXPIRATION", dotenv.get("TOKEN_EXPIRATION"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        System.setProperty("CLOUDINARY_CLOUDNAME", dotenv.get("CLOUDINARY_CLOUDNAME"));
        System.setProperty("CLOUDINARY_APIKEY", dotenv.get("CLOUDINARY_APIKEY"));
        System.setProperty("CLOUDINARY_APISECRET", dotenv.get("CLOUDINARY_APISECRET"));
        System.setProperty("CLOUDINARY_URL", dotenv.get("CLOUDINARY_URL"));*/
        SpringApplication.run(PetrohubProjectApplication.class, args);
    }

}
