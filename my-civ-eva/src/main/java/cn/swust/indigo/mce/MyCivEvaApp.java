package cn.swust.indigo.mce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lhz
 */
@SpringBootApplication(scanBasePackages = "cn.swust.indigo")
@EnableCaching
@EnableWebSecurity
@EnableScheduling
@EnableTransactionManagement
public class MyCivEvaApp {
    public static void main(String[] args) {
        SpringApplication.run(MyCivEvaApp.class, args);
    }
}
