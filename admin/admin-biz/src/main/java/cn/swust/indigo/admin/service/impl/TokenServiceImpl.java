package cn.swust.indigo.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.Token;
import cn.swust.indigo.admin.service.TokenService;
import cn.swust.indigo.common.core.constants.CacheConstants;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final CacheManager cacheManager;

    @Override
    public UserInfo getLoginUserByUserName(String username) {
        Cache userCache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        UserInfo userInfo = (UserInfo) userCache.get(username);
        return userInfo;
    }

    @Override
    public Token saveToken(int userId, String username) {
        String accessToken = IdUtil.randomUUID();
        Token token = new Token(userId, username, accessToken, IdUtil.randomUUID());
        Cache cache = cacheManager.getCache(CacheConstants.USER_TOKEN);
        cache.put(accessToken, token);
        return token;
    }

    @Override
    public Token refresh(String accessToken, String refreshToken) {
        Cache cache = cacheManager.getCache(CacheConstants.USER_TOKEN);
        Token token = (Token) cache.get(accessToken);
        if (token != null) {
            if (token.getRefreshToken().equals(refreshToken)) {
                String newAccessToken = IdUtil.randomUUID();
                Token newToken = new Token(token.getUserId(),
                        token.getUsername(), newAccessToken, IdUtil.randomUUID());
                cache.put(newAccessToken, token);
                cache.evict(accessToken);
                return newToken;
            }
        }
        return null;
    }

    @Override
    public UserInfo getLoginUser(String accessToken) {
        Cache cache = cacheManager.getCache(CacheConstants.USER_TOKEN);
        Token token = (Token) cache.get(accessToken);
        if (token != null) {
            Cache userCache = cacheManager.getCache(CacheConstants.USER_DETAILS);
            UserInfo userInfo = (UserInfo) userCache.get(token.getUsername());
            return userInfo;
        } else
            return null;
    }

    @Override
    public void deleteToken(String accessToken) {
        Cache cache = cacheManager.getCache(CacheConstants.USER_TOKEN);
        Token token = (Token) cache.get(accessToken).get();
        if (token != null) {
            Cache userCache = cacheManager.getCache(CacheConstants.USER_DETAILS);
            userCache.evict(token.getUsername());
        }
        cache.evict(accessToken);
    }
}
