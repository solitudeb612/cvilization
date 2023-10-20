package cn.swust.indigo.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.swust.indigo.admin.entity.dto.UserDto;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.*;
import cn.swust.indigo.admin.entity.vo.MenuVO;
import cn.swust.indigo.admin.entity.vo.UserVO;
import cn.swust.indigo.admin.mapper.SysUserMapper;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.constants.CommonConstants;
import cn.swust.indigo.common.data.mybatis.datascope.DataScope;
import cn.swust.indigo.admin.service.*;
import cn.swust.indigo.common.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户信息
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;
    private final SysDeptService sysDeptService;
    private final SysUserRoleService sysUserRoleService;
    private final SysDeptRelationService sysDeptRelationService;
    private final SysUserMapper sysUserMapper;
    private TokenService tokenService;

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);

        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUser);
        List<SysUserRole> userRoleList = userDto.getRole()
                .stream().map(roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(sysUser.getUserId());
                    userRole.setRoleId(roleId);
                    return userRole;
                }).collect(Collectors.toList());
        return sysUserRoleService.saveBatch(userRoleList);
    }

    /***
     * 重置密码
     * @param userId
     * @return
     */
    @Override
    public Boolean resetPsw(Integer userId) {
        UserVO user = this.selectUserVoById(userId);
        if (user == null) {
            return false;
        }
        String psw = ENCODER.encode("88888888");
        return baseMapper.changePassword(userId, psw) > 0 ? true : false;
    }

    /**
     * 不分页查询用户信息（含角色）
     *
     * @param userDTO 查询参数
     * @return list
     */
    @Override
    public List<UserVO> getUserVosList(UserDto userDTO) {
        return baseMapper.getUserVosList(userDTO, new DataScope());
    }

    @Override
    public SysUser findSysUserByUserName(String username) {
        return  sysUserMapper.findSysUserByUserName(username);
    }

    /**
     * 通过查用户的全部信息
     *
     * @param sysUser 用户
     * @return
     */
    @Override
    public UserInfo findUserInfo(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        List<SysRole> rolesByUserIds = sysRoleService.findRolesByUserId(sysUser.getUserId());

        //设置角色列表  （ID）
        List<Integer> roleIds = rolesByUserIds
                .stream()
                .map(SysRole::getRoleId)
                .collect(Collectors.toList());
        userInfo.setRoles(ArrayUtil.toArray(roleIds, Integer.class));
        List<String> roleNames = rolesByUserIds.stream().map(SysRole::getRoleName).collect(Collectors.toList());//设置权限列表（menu.permission）
        userInfo.setRoleNames(ArrayUtil.toArray(roleNames,String.class));
        Set<String> permissions = new HashSet<>();
        roleIds.forEach(roleId -> {
            List<String> permissionList = sysMenuService.findMenuByRoleId(roleId)
                    .stream()
                    .filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
                    .map(MenuVO::getPermission)
                    .collect(Collectors.toList());
            permissions.addAll(permissionList);
        });
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));

        return userInfo;
    }

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return
     */
    @Override
    public IPage getUsersWithRolePage(Page page, UserDto userDTO) {
        long current = page.getCurrent() - 1;
        long size = page.getSize();
        long beforeSize = current * size;
        List<UserVO> userVosPage = baseMapper.getUserVosPage(beforeSize, size, userDTO, new DataScope());
        int count = baseMapper.selectTotal(beforeSize, size, userDTO, new DataScope());
        page.setTotal(count);
        return page.setRecords(userVosPage);
    }

    @Override
    public IPage getNotLeader(Page page, UserDto userDTO) {
        long current = page.getCurrent() - 1;
        long size = page.getSize();
        long beforeSize = current * size;
        List<UserVO> userVosPage = sysUserMapper.getNotLeaderUser(beforeSize, size, userDTO, new DataScope());
        int count = baseMapper.selectTotal(beforeSize, size, userDTO, new DataScope());
        page.setTotal(count);
        return page.setRecords(userVosPage);
    }

    @Override
    public boolean unite(String openId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getWxOpenId, openId);
        List<SysUser> sysUsers = sysUserMapper.selectList(wrapper);
        List<SysUser> collect = sysUsers.stream().peek(user -> user.setWxOpenId(null)).collect(Collectors.toList());
        return this.updateBatchById(collect);
    }

    @Override
    public boolean uniteByUserId(Integer userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (ObjectUtil.isNotNull(sysUser)){
            sysUser.setWxOpenId(null);
            sysUserMapper.updateById(sysUser);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserVO selectUserVoById(Integer id) {
        return baseMapper.getUserVoById(id);
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
    public Boolean deleteUserById(SysUser sysUser) {
        sysUserRoleService.deleteByUserId(sysUser.getUserId());
        this.removeById(sysUser.getUserId());
        return Boolean.TRUE;
    }

    /***
     *
     * @param userDto 用户信息
     * 通过用户姓名和原密码修改新密码
     * @return
     */
    @Override
//    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
    public boolean updateUserInfo(UserDto userDto) {
        UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());
        SysUser sysUser = new SysUser();
//		if (StrUtil.isNotBlank(userDto.getPassword())
//				&& StrUtil.isNotBlank(userDto.getNewpassword1())) {
//			if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
//				sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
//			} else {
//				log.warn("原密码错误，修改密码失败:{}", userDto.getUsername());
//				return R.ok(Boolean.FALSE, "原密码错误，修改失败");
//			}
//		}
        sysUser.setPhone(userDto.getPhone());
        sysUser.setUserId(userVO.getUserId());
        sysUser.setEmail(userDto.getEmail());
        sysUser.setSex(userDto.getSex());
        sysUser.setNickname(userDto.getNickname());
        // 修改个人信息后，直接更新缓存中的用户数据
        updateUserInfo(sysUser);
        return this.updateById(updateUserInfo(sysUser));
    }

    @CachePut(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
    public SysUser updateUserInfo(SysUser sysUser){
        return sysUser;
    }


    /***
     *
     * @param userDto 用户信息
     * 修改用户信息再修改权限
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
    public Boolean updateUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(LocalDateTime.now());

        if (StrUtil.isNotBlank(userDto.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        }
        this.updateById(sysUser);

        sysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
                .eq(SysUserRole::getUserId, userDto.getUserId()));
        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return Boolean.TRUE;
    }

    /**
     * 查询上级部门的用户信息
     *
     * @param username 用户名
     * @return R
     */
    @Override
    public List<SysUser> listAncestorUsers(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda()
                .eq(SysUser::getUsername, username));

        SysDept sysDept = sysDeptService.getById(sysUser.getDepartmentId());
        if (sysDept == null) {
            return null;
        }

        Integer parentId = sysDept.getParentId();
        return this.list(Wrappers.<SysUser>query().lambda()
                .eq(SysUser::getDepartmentId, parentId));
    }

    /**
     * 获取当前用户的子部门信息
     *
     * @return 子部门列表
     */
    private List<Integer> getChildDepts(Integer deptId) {
        //获取当前部门的子部门
        return sysDeptRelationService
                .list(Wrappers.<SysDeptRelation>query().lambda()
                        .eq(SysDeptRelation::getAncestor, deptId))
                .stream()
                .map(SysDeptRelation::getDescendant)
                .collect(Collectors.toList());
    }

    /*
     *修改用户密码
     *@param 用户userId 新密码newPsw 旧密码newPsw
     * @return true/false
     */
    public Boolean changePsw(Integer userId, String oldPsw, String newPsw,String token) {
        UserVO sysuser = baseMapper.getUserVoById(userId);
        ENCODER.encode(oldPsw);
        if (ENCODER.matches(oldPsw, sysuser.getPassword())) {
            String psw = ENCODER.encode(newPsw);
            tokenService.deleteToken(token);
            return baseMapper.changePassword(userId, psw) > 0 ? true : false;
        }
        return false;
    }

    @Override
    public UserInfo getByUserName(String username) {
        UserVO userVo = sysUserMapper.getUserVoByUsername(username);
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userVo, user);
        UserInfo userInfo = this.findUserInfo(user);
        return userInfo;
    }
    @Override
    public UserInfo getByWxOpenId(String openId) {
        UserVO userVo = sysUserMapper.getUserVoByWxOpenId(openId);
        if(userVo == null){
            return null;
        }
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userVo, user);
        UserInfo userInfo = this.findUserInfo(user);
        return userInfo;
    }
}