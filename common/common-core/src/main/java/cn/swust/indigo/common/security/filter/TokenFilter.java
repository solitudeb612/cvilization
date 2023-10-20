package cn.swust.indigo.common.security.filter;

import cn.swust.indigo.admin.entity.bo.LoginUser;
import cn.swust.indigo.admin.entity.po.Token;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.security.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token过滤器
 */
@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final CacheManager cacheManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = AuthUtils.extractAndDecodeHeader(request);
        if (StringUtils.isNotBlank(accessToken)) {
            Cache tokenCache = cacheManager.getCache(CacheConstants.USER_TOKEN);
            if (tokenCache != null && tokenCache.get(accessToken) != null) {
                Token token = (Token) tokenCache.get(accessToken).get();
                Cache userCache = cacheManager.getCache(CacheConstants.USER_DETAILS);
                LoginUser loginUser = (LoginUser) userCache.get(token.getUsername()).get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser,
                        null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}
