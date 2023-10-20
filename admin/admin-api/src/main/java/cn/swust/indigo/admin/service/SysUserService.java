package cn.swust.indigo.admin.service;

import cn.swust.indigo.admin.entity.bo.LoginUser;
import cn.swust.indigo.admin.entity.dto.UserDto;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.vo.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户信息
 */
public interface SysUserService extends IService<SysUser> {
    UserInfo getByUserName(String username);
    UserInfo getByWxOpenId(String openId);
    /**
     * 查询用户信息
     *
     * @param sysUser 用户
     * @return userInfo
     */
    UserInfo findUserInfo(SysUser sysUser);

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return
     */
    IPage getUsersWithRolePage(Page page, UserDto userDTO);

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return boolean
     */
    Boolean deleteUserById(SysUser sysUser);

    /**
     * 更新当前用户基本信息
     *
     * @param userDto 用户信息
     * @return Boolean
     */
    boolean updateUserInfo(UserDto userDto);

    /**
     * 更新指定用户信息
     *
     * @param userDto 用户信息
     * @return true/false
     */
    Boolean updateUser(UserDto userDto);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO selectUserVoById(Integer id);

    /**
     * 查询上级部门的用户信息
     *
     * @param username 用户名
     * @return R
     */
    List<SysUser> listAncestorUsers(String username);

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    Boolean saveUser(UserDto userDto);

    /*
     * 修改用户密码
     * @param 用户self 新密码newPsw 旧密码newPsw
     * @return true/false
     */
    Boolean changePsw(Integer userId, String oldPsw, String newPsw,String token);

    /**
     * 更新指定用户信息
     *
     * @param userId
     * @return
     */
    Boolean resetPsw(Integer userId);

    /**
     * 不分页查询用户信息（含角色）
     *
     * @param userDTO 查询参数
     * @return list
     */
    List<UserVO> getUserVosList(UserDto userDTO);

    SysUser findSysUserByUserName(String username);

    IPage getNotLeader(Page page, UserDto userDTO);

    @Deprecated
    boolean unite(String openId);

    boolean uniteByUserId(Integer userId);
}
