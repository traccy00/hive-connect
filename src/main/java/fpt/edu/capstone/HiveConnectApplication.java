package fpt.edu.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class HiveConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiveConnectApplication.class, args);
    }

}
