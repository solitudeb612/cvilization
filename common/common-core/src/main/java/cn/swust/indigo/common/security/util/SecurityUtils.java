package cn.swust.indigo.common.security.util;

import cn.hutool.core.util.StrUtil;
import cn.swust.indigo.admin.entity.bo.LoginUser;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.common.core.constants.SecurityConstants;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具类
 */
@UtilityClass
public class SecurityUtils {
    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 获取用户
     *
     * @param authentication
     * @return LoginUser
     * <p>
     * 获取当前用户的全部信息
     * 获取当前用户的用户名
     */
    public LoginUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUser) {
            return (LoginUser) principal;
        }
        return null;
    }

    /**
     * 获取用户
     *
     * @return
     */
    public UserInfo getUser() {
        Authentication authentication = getAuthentication();
        LoginUser loginUser = getUser(authentication);
        if (loginUser == null)
            return null;
        else
            return loginUser.getUserInfo();
    }


    /**
     * 获取用户角色信息
     *
     * @return 角色集合
     */
    public List<Integer> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<Integer> roleIds = new ArrayList<>();
        authorities.stream()
                .filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
                .forEach(granted -> {
                    String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE);
                    roleIds.add(Integer.parseInt(id));
                });
        return roleIds;
    }
}
