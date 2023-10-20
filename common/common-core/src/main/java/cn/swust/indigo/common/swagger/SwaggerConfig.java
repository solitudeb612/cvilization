package cn.swust.indigo.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置配置
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    static List<ResponseMessage> getResponseMsg() {
        List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
        responseMessages.add(new ResponseMessageBuilder().code(HttpServletResponse.SC_OK).message("操作成功").build());
        responseMessages.add(new ResponseMessageBuilder().code(HttpServletResponse.SC_NOT_FOUND).message("资源不存在").build());
        responseMessages.add(new ResponseMessageBuilder().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).message("服务器异常").build());
        return responseMessages;
    }

    static List<Parameter> getParamList() {
        List<Parameter> pars = new ArrayList<Parameter>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("访问令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/swagger/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean
    public Docket adminDocket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("基础信息管理")
                .apiInfo(adminInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.PUT, getResponseMsg())
                .globalResponseMessage(RequestMethod.GET, getResponseMsg())
                .globalResponseMessage(RequestMethod.POST, getResponseMsg())
                .globalResponseMessage(RequestMethod.DELETE, getResponseMsg())
                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors
                        .basePackage("cn.swust.indigo.admin.controller"))   // 指定controller包
                .paths(PathSelectors.any())
                .build().globalOperationParameters(SwaggerConfig.getParamList());
    }

    ApiInfo adminInfo() {
        return new ApiInfoBuilder()
                .title("系统——基础信息管理")
                .description("用户、角色、字典、菜单管理")
                .version("0.1")
                .build();
    }


    @Bean
    public Docket appDocket(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("绵阳市宣传部文明办文明评测系统")
                .apiInfo(paGuideInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.PUT, getResponseMsg())
                .globalResponseMessage(RequestMethod.GET, getResponseMsg())
                .globalResponseMessage(RequestMethod.POST, getResponseMsg())
                .globalResponseMessage(RequestMethod.DELETE, getResponseMsg())
                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors
                        .basePackage("cn.swust.indigo.mce.controller"))   // 指定controller包
                .paths(PathSelectors.any())
                .build().globalOperationParameters(SwaggerConfig.getParamList());
    }

    ApiInfo paGuideInfo() {
        return new ApiInfoBuilder()
                .title("绵阳市宣传部文明办文明评测系统")
                .description("绵阳市宣传部文明办文明评测系统")
                .version("0.1")
                .build();
    }

}
