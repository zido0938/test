package com.goldstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // @CreatedDate와 @LastModifiedDate가 동작하게 함
public class SaboteurApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaboteurApplication.class, args);
    }
}
