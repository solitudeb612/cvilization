package cn.swust.indigo.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = "cn.swust.indigo")
@EnableCaching
@EnableWebSecurity
public class AdminApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplicationTest.class, args);
    }

}
