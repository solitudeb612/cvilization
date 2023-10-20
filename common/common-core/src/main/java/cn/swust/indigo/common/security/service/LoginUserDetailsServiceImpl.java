package cn.swust.indigo.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.swust.indigo.admin.entity.bo.LoginUser;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.vo.UserVO;
import cn.swust.indigo.admin.service.SysUserService;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.constants.CommonConstants;
import cn.swust.indigo.common.core.constants.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户详细信息
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoginUserDetailsServiceImpl implements UserDetailsService {
    private final SysUserService sysUserService;
    private final CacheManager cacheManager;

    /**
     * 用户密码登录
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        if (cache != null && cache.get(username) != null) {
            return (LoginUser) cache.get(username).get();
        }
        UserInfo user = sysUserService.getByUserName(username);
        UserDetails userDetails = getUserDetails(user);
        cache.put(username, userDetails);
        return userDetails;
    }


    /**
     * 构建userdetails
     *
     * @return
     */
    private UserDetails getUserDetails(UserInfo user) {
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Set<String> dbAuthsSet = new HashSet<>();
        if (ArrayUtil.isNotEmpty(user.getRoles())) {
            // 获取角色
            Arrays.stream(user.getRoles()).forEach(roleId -> dbAuthsSet.add(SecurityConstants.ROLE + roleId));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(user.getPermissions()));
        }
        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUser sysUser = user.getSysUser();
        boolean enabled = StrUtil.equals(sysUser.getStatus(), CommonConstants.STATUS_NORMAL);
        // 构造security用户
        return new LoginUser(user);
    }
}
