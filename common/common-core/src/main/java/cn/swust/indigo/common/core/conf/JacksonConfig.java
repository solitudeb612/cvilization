package cn.swust.indigo.common.core.conf;

import cn.hutool.core.date.DatePattern;
import cn.swust.indigo.common.core.jackson.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * JacksonConfig
 */
@Configuration
@Primary
@ConditionalOnClass({ObjectMapper.class, JavaTimeModule.class})
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        Jackson2ObjectMapperBuilderCustomizer builderCustomizer = new Jackson2ObjectMapperBuilderCustomizer(){

            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.locale(Locale.CHINA);
                jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
                jacksonObjectMapperBuilder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
                jacksonObjectMapperBuilder.modules(new JavaTimeModule());
            }
        };
        return  builderCustomizer;

//        return builder -> {
//            builder.locale(Locale.CHINA);
//            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
//            builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
//            builder.modules(new JavaTimeModule());
//        };
    }
}
