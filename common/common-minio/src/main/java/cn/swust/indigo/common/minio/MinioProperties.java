package cn.swust.indigo.common.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * cn.swust.indigo.common.minio 配置信息
 */
@Data
@ConfigurationProperties(prefix = "minio")
@Component
@Primary
@PropertySource(value={"classpath:application.properties"})
public class MinioProperties {
    /**
     * 服务地址 http://ip:port
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;
    /**
     * 桶名称
     */
    private String bucketName;


}
