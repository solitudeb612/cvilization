package cn.swust.indigo.common.security.handler;

import cn.hutool.json.JSONUtil;
import cn.swust.indigo.admin.entity.bo.LoginUser;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.Token;
import cn.swust.indigo.admin.service.TokenService;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.security.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * spring security处理器
 */
@Configuration
@AllArgsConstructor
public class SecurityHandlerConfig {
    private final TokenService tokenService;
    private final CacheManager cacheManager;

    /**
     * 登陆成功，返回Token
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            LoginUser loginUser = (LoginUser)
                    authentication.getPrincipal();
            Token token = tokenService.saveToken(loginUser.getUserId(), loginUser.getUsername());
            Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
            cache.put(loginUser.getUsername(), loginUser);
            Cache token_cache = cacheManager.getCache(CacheConstants.USER_TOKEN);
            token_cache.put(token.getAccessToken(), token);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            response.getWriter().write(JSONUtil.toJsonStr(R.ok(token)));
        };
    }

    /**
     * 登陆失败
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            String msg = null;
            if (exception instanceof BadCredentialsException) {
                msg = "密码错误!";
            } else {
                msg = exception.getMessage();
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            response.getWriter().write(JSONUtil.toJsonStr(R.failed(msg)));
        };

    }

    /**
     * 未登录，返回401
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(JSONUtil.toJsonStr(R.failed("未授权的登录活动相应")));
            }
        };
    }

    /**
     * 退出处理
     *
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSussHandler() {
        return (request, response, authentication) -> {
            String token = AuthUtils.extractAndDecodeHeader(request);
            if (token != null) {
                UserInfo loginUser = tokenService.getLoginUser(token);
                if (loginUser != null) {
                    tokenService.deleteToken(token);
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            response.getWriter().write(JSONUtil.toJsonStr(R.ok("退出成功！")));

        };

    }

}
