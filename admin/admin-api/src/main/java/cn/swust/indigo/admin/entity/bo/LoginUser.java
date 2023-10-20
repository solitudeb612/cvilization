package cn.swust.indigo.admin.entity.bo;

import cn.swust.indigo.admin.entity.dto.UserInfo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import cn.swust.indigo.admin.constants.UserStatusConstants;
import java.util.Collection;

/**
 * 扩展用户信息
 */


public class LoginUser extends User {
    /**
     * 用户ID
     */

    private Integer userId;
    /**
     * 部门ID
     */
    private Integer departmentId;


    private UserInfo userInfo;

    /**
     * Construct the <code>User</code> with the details required by
     *
     * @param id                    用户ID
     * @param deptId                部门ID
     * @param username              the username presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param password              the password that should be presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param enabled               set to <code>true</code> if the user is enabled
     * @param accountNonExpired     set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not
     *                              expired
     * @param accountNonLocked      set to <code>true</code> if the account is not locked
     * @param authorities           the authorities that should be granted to the caller if they
     *                              presented the correct username and password and the user is enabled. Not null.
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     *                                  a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    public LoginUser(Integer id, Integer deptId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = id;
        this.departmentId = deptId;
    }

    public LoginUser(UserInfo userInfo) {
        super(userInfo.getSysUser().getUsername(),
                userInfo.getSysUser().getPassword(),
                true,
                true,
                true,
                !UserStatusConstants.STATUS_LOCK.equals(userInfo.getSysUser().getStatus()),
                AuthorityUtils.createAuthorityList(userInfo.getPermissions()));
        this.userId = userInfo.getSysUser().getUserId();
        this.departmentId = userInfo.getSysUser().getDepartmentId();
        this.userInfo = userInfo;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}