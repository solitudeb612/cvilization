package cn.swust.indigo.admin.controller;

import cn.swust.indigo.admin.entity.dto.SysRoleMenus;
import cn.swust.indigo.admin.entity.po.SysRole;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.vo.QSysRole;
import cn.swust.indigo.admin.service.SysRoleMenuService;
import cn.swust.indigo.admin.service.SysRoleService;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 角色管理模块
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/roles")
@Api(value = "role", tags = "角色管理模块")
public class SysRoleController {
    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;

//    /**
//     * 通过ID查询角色信息
//     *
//     * @param id ID
//     * @return 角色信息
//     */
//    @ApiOperation(value = "查询角色信息", notes = "通过ID查询角色信息")
//    @GetMapping("/{id}")
//    public R getById(@PathVariable Integer id) {
//        return R.ok(sysRoleService.getById(id));
//    }

    /**
     * 通过ID查询角色信息
     *
     * @param roleId ID
     * @return 角色信息
     */
    @ApiOperation(value = "查询角色信息", notes = "通过ID查询角色信息")
    @GetMapping("/get")
    public R getByParamId(@RequestParam("roleId") @NotNull(message = "id不能为空") Integer roleId) {
        return R.ok(sysRoleService.getById(roleId));
    }

    /**
     * 添加角色
     *
     * @param sysRole 角色信息
     * @return success、false
     */
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @Log("添加角色")
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('sys_role_add')")
    public R save(@Valid @RequestBody SysRole sysRole) {
        SysUser sysUser = SecurityUtils.getUser().getSysUser();
        sysRole.setCreatorId(sysUser.getUserId());
        sysRole.setUpdaterId(sysUser.getUserId());
        return R.ok(sysRoleService.save(sysRole));
    }

    /**
     * 修改角色
     *
     * @param sysRole 角色信息
     * @return success/false
     */
    @ApiOperation(value = "修改角色", notes = "修改角色")
    @Log("修改角色")
    @PutMapping("/edit")
//    @PreAuthorize("hasAuthority('sys_role_edit')")
    public R update(@Valid @RequestBody SysRole sysRole) {
        sysRole.setUpdaterId(SecurityUtils.getUser().getSysUser().getUserId());
        return R.ok(sysRoleService.updateById(sysRole));
    }

//    /**
//     * 删除角色
//     *
//     * @param id
//     * @return
//     */
//    @ApiOperation(value = "删除角色", notes = "通过id删除角色")
//    @Log("删除角色")
//    @DeleteMapping("/{id}")
////    @PreAuthorize("hasAuthority('sys_role_del')")
//    public R removeById(@PathVariable Integer id) {
//        return R.ok(sysRoleService.removeRoleById(id));
//    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "通过id删除角色")
    @Log("删除角色")
    @DeleteMapping("/del")
//    @PreAuthorize("hasAuthority('sys_role_del')")
    public R removeByParamId(@RequestParam("roleId") @NotNull(message = "id不能为空") Integer roleId) {
        return R.ok(sysRoleService.removeRoleById(roleId));
    }

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @GetMapping("/list")
    public R listRoles() {
        return R.ok(sysRoleService.list(Wrappers.emptyWrapper()));
    }

    /**
     * 分页查询角色信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询字典信息", notes = "分页查询字典信息")
    public R getRolePage(Page<SysRole> page, QSysRole sysRole) {
        return R.ok(sysRoleService.page(page, CreateQuery.getQueryWrapper(sysRole)));
    }

    /**
     * 更新角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return success、false
     */
    @ApiOperation(value = "更新角色菜单", notes = "更新角色菜单")
    @Log("更新角色菜单")
    @PutMapping("/menu")
//    @PreAuthorize("hasAuthority('sys_role_perm')")
    public R saveRoleMenus(Integer roleId, @RequestParam(value = "menuIds", required = false) String menuIds) {
        SysRole sysRole = sysRoleService.getById(roleId);
        return R.ok(sysRoleMenuService.saveRoleMenus(sysRole.getRoleCode(), roleId, menuIds));
    }

    /**
     * @return
     */
    @ApiOperation(value = "更新角色菜单（设置功能权限）", notes = "更新角色菜单（设置功能权限）")
    @Log("更新角色菜单")
    @PutMapping("/set")
//    @PreAuthorize("hasAuthority('sys_role_perm')")
    public R saveRoleMenu(@Valid @RequestBody SysRoleMenus sysRoleMenus) {
        SysRole sysRole = sysRoleService.getById(sysRoleMenus.getRoleId());
        return R.ok(sysRoleMenuService.saveRoleMenus(sysRole.getRoleCode(), sysRoleMenus.getRoleId(), sysRoleMenus.getMenuIds()));
    }

}
