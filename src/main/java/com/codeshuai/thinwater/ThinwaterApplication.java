package com.codeshuai.thinwater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ThinwaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinwaterApplication.class, args);
    }

}
