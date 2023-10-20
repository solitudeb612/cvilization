package cn.swust.indigo.admin.service;


import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.Token;

/**
 * Token管理器<br>
 * 可存储到redis或者数据库<br>
 */
public interface TokenService {

    Token saveToken(int userId, String username);

    Token refresh(String accessToken, String refreshToken);

    UserInfo getLoginUser(String accessToken);

    void deleteToken(String accessToken);

    UserInfo getLoginUserByUserName(String username);

}
