package cn.swust.indigo.admin.controller;

import cn.swust.indigo.admin.entity.dto.ChangePsw;
import cn.swust.indigo.admin.entity.dto.UserDto;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.vo.QSysUser;
import cn.swust.indigo.admin.entity.vo.UserVO;
import cn.swust.indigo.admin.service.SysUserService;
import cn.swust.indigo.admin.service.TokenService;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.security.util.AuthUtils;
import cn.swust.indigo.common.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户管理模块
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/user")
@Api(value = "user", tags = "用户管理模块")
public class SysUserController {
    private final SysUserService userService;


    /**
     * 获取指定用户全部信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @ApiOperation(value = "获取指定用户全部信息", notes = "获取指定用户全部信息")
    @GetMapping("/info/{username}")
    public R info(@PathVariable String username) {

        SysUser user = userService.findSysUserByUserName(username);
        System.out.println(user);
        if (user == null) {
            return R.failed(null, String.format("用户信息为空 %s", username));
        }

        return R.ok(userService.findUserInfo(user));
    }


    /**
     * 通过ID查询用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @ApiOperation(value = "查询用户信息", notes = "通过ID查询用户信息")
    @GetMapping("/{id}")
    public R user(@RequestParam("id") Integer id) {
        return R.ok(userService.selectUserVoById(id));
    }


    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return
     */
    @ApiOperation(value = "查询用户信息", notes = "根据用户名查询用户信息")
    @GetMapping("/details/{username}")
    public R user(@PathVariable String username) {
        SysUser condition = new SysUser();
        condition.setUsername(username);
        return R.ok(userService.getOne(new QueryWrapper<>(condition)));
    }


    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */


    @Log("删除用户信息")
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('sys_user_del')")
    @ApiOperation(value = "删除用户", notes = "根据ID删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "path")
    public R userDel(@PathVariable Integer id) {
        SysUser sysUser = userService.getById(id);
        return R.ok(userService.deleteUserById(sysUser));
    }


    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @Log("添加用户")
    @ApiOperation(value = "添加用户", notes = "添加用户")
    @PostMapping
//    @PreAuthorize("hasAuthority('sys_user_add')")
    public R user(@RequestBody UserDto userDto) {
        return R.ok(userService.saveUser(userDto));
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @Log("更新用户信息")
    @PutMapping
//    @PreAuthorize("hasAuthority('sys_user_edit')")
    public R updateUser(@Valid @RequestBody UserDto userDto) {
        return R.ok(userService.updateUser(userDto));
    }

    /**
     * 分页查询用户
     *
     * @param page    参数集
     * @param userDTO 查询参数列表
     * @return 用户集合
     */
    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    @GetMapping("/page")
    public R getUserPage(Page page, UserDto userDTO) {
        return R.ok(userService.getUsersWithRolePage(page, userDTO));
    }

    /**
     * 分页查询非leader用户
     *
     * @param page    参数集
     * @param userDTO 查询参数列表
     * @return 用户集合
     */
    @ApiOperation(value = "分页查询非leader用户", notes = "分页查询非leader用户")
    @GetMapping("/normal/page")
    public R getNotLeaderPage(Page page, UserDto userDTO) {
        return R.ok(userService.getNotLeader(page, userDTO));
    }


    /**
     * 修改个人信息
     *
     * @param userDto userDto
     * @return success/false
     */
    @ApiOperation(value = "修改个人信息", notes = "修改个人信息")
    @Log("修改个人信息")
    @PutMapping("/edit")
    public R updateUserInfo(@Valid @RequestBody UserDto userDto) {
        boolean result = userService.updateUserInfo(userDto);
        if (result) {
            return R.ok();
        } else {
            return R.failed("更新失败");
        }
    }

    /**
     * @param username 用户名称
     * @return 上级部门用户列表
     */
    @ApiOperation(value = "用户名称", notes = "用户名称")
    @GetMapping("/ancestor/{username}")
    public R listAncestorUsers(@PathVariable String username) {
        return R.ok(userService.listAncestorUsers(username));
    }

    /**
     * @param username 用户名称
     * @return 上级部门用户列表
     */
    @ApiOperation(value = "用户名称", notes = "用户名称")
    @GetMapping("/ancestor/username")
    public R listAncestorUsersByName(@RequestParam("username") @NotNull(message = "username不能为空") String username) {
        return R.ok(userService.listAncestorUsers(username));
    }

    /*
     *修改用户密码
     * @param ChangePsw 对象
     * @return true/false
     */
    @ApiOperation(value = "修改密码")
    @PutMapping("/changePsw")
    public R changePsw(@Valid @RequestBody ChangePsw changePsw, HttpServletRequest request) {
        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();
        String token = AuthUtils.extractAndDecodeHeader(request);
        return R.ok(
                userService.changePsw(userId, changePsw.getOldPsw(), changePsw.getNewPsw(),token)
        );
    }
    /***
     *重置密码
     * @param changePsw
     * @return true/false
     */
    @ApiOperation(value = "重置密码", notes = "重制密码，需要退出登录")
    @Log("重置密码")
    @PutMapping("/resetPsw")
    public R resetPsw(@RequestBody Integer userId) {
        return R.ok(userService.resetPsw(userId));
    }

    /**
     * 不分页用户查询
     *
     * @param qSysUser 用户查询对象
     * @return 不分页用户查询
     */
    @GetMapping("/list")
    @ApiOperation(value = "不分页查询")
    public R list(QSysUser qSysUser) {
        return R.ok(userService.list(CreateQuery.getQueryWrapper(qSysUser)));
    }

    /**
     * 不分页查询用户角色
     * <p>
     * * @param userVO                  用户查询对象
     *
     * @return 不分页用户查询
     */
    @GetMapping("/list/user/role")
    @ApiOperation(value = "不分页查询")
    public R listUserRole(UserDto userDTO) {
        return R.ok(userService.getUserVosList(userDTO));
    }
}
