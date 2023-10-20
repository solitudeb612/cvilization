package cn.swust.indigo.common.minio.conf;

import cn.swust.indigo.common.minio.MinioProperties;
import cn.swust.indigo.common.minio.service.MinioTemplate;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio 自动配置类
 */

@AllArgsConstructor
@Configuration
@ConditionalOnMissingBean(MinioTemplate.class)
//@ConditionalOnProperty(prefix = "minio")
@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {
    private final MinioProperties properties;

    @Bean
    MinioTemplate template() {
        return new MinioTemplate(
                properties.getUrl(),
                properties.getAccessKey(),
                properties.getSecretKey()
        );
    }

}
