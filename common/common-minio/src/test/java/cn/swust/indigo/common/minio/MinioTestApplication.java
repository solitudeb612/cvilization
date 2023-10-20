package cn.swust.indigo.common.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "cn.swust")
public class MinioTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinioTestApplication.class, args);
    }
}
