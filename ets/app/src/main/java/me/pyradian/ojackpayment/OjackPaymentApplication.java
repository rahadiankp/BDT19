package me.pyradian.ojackpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OjackPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjackPaymentApplication.class, args);
    }

}
