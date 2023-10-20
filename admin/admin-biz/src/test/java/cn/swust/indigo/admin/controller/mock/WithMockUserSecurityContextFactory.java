package cn.swust.indigo.admin.controller.mock;

import cn.swust.indigo.admin.entity.bo.LoginUser;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@AllArgsConstructor
public class WithMockUserSecurityContextFactory
        implements WithSecurityContextFactory<MockAdminUser> {

    private final String username = "admin";

    @Override
    public SecurityContext createSecurityContext(MockAdminUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication auth = null;
        SysUser sysUser = new SysUser();
        sysUser.setPassword("88888888");
        sysUser.setUserId(1);
        sysUser.setUsername("admin");
        sysUser.setDepartmentId(1);
        sysUser.setStatus("0");
        UserInfo userInfo = new UserInfo();
        userInfo.setPermissions(new String[]{"sys_dict_edit"});
        userInfo.setRoles(new Integer[]{1});
        userInfo.setSysUser(sysUser);

        LoginUser user = new LoginUser(userInfo);
        auth = new UsernamePasswordAuthenticationToken(user, "8888888", user.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}