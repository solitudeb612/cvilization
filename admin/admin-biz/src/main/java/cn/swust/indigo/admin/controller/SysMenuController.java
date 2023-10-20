package cn.swust.indigo.admin.controller;


import cn.swust.indigo.admin.entity.dto.MenuTree;
import cn.swust.indigo.admin.entity.po.SysMenu;
import cn.swust.indigo.admin.entity.vo.MenuVO;
import cn.swust.indigo.admin.entity.vo.QSysMenu;
import cn.swust.indigo.admin.entity.vo.TreeUtil;
import cn.swust.indigo.admin.service.SysMenuService;
import cn.swust.indigo.common.core.constants.CommonConstants;
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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单管理模块
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/menu")
@Api(value = "menu", tags = "菜单管理模块")
public class SysMenuController {
    private final SysMenuService sysMenuService;

    /**
     * 返回当前用户的树形菜单集合
     *
     * @return 当前用户的树形菜单
     */
    @ApiOperation(value = "当前用户的树形菜单集合", notes = "返回当前用户的树形菜单集合")
    @GetMapping
    public R getUserMenu() {
        // 获取符合条件的菜单
        Set<MenuVO> all = new HashSet<>();
        SecurityUtils.getRoles()
                .forEach(roleId -> all.addAll(sysMenuService.findMenuByRoleId(roleId)));
        List<MenuTree> menuTreeList = all.stream()
                .filter(menuVo -> CommonConstants.MENU.equals(menuVo.getType()))
                .map(MenuTree::new)
                .sorted(Comparator.comparingInt(MenuTree::getSort))
                .collect(Collectors.toList());
        return R.ok(TreeUtil.build(menuTreeList, CommonConstants.MENU_TREE_ROOT_ID));
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @ApiOperation(value = "树形菜单集合", notes = "返回树形菜单集合")
    @GetMapping(value = "/tree")
    public R getTree() {
        return R.ok(TreeUtil.buildTree(sysMenuService
                .list(Wrappers.<SysMenu>lambdaQuery()
                        .orderByAsc(SysMenu::getSort)), CommonConstants.MENU_TREE_ROOT_ID));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId 角色ID
     * @return 属性集合
     */
    @ApiOperation(value = "角色的菜单集合", notes = "返回角色的菜单集合")
    @GetMapping("/tree/{roleId}")
    public R getRoleTree(@PathVariable Integer roleId) {
        return R.ok(sysMenuService.findMenuByRoleId(roleId)
                .stream()
                .map(MenuVO::getMenuId)
                .collect(Collectors.toList()));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId 角色ID
     * @return 属性集合
     */
    @ApiOperation(value = "角色的菜单集合", notes = "返回角色的菜单集合")
    @GetMapping("/tree/roleId")
    public R getRoleTreeByRoleId(@RequestParam("roleId") @NotNull(message = "roleId不能为空") Integer roleId) {
        return R.ok(sysMenuService.findMenuByRoleId(roleId)
                .stream()
                .map(MenuVO::getMenuId)
                .collect(Collectors.toList()));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId 角色ID
     * @return 属性集合
     */
    @ApiOperation(value = "角色的菜单集合", notes = "返回角色的菜单集合")
    @GetMapping("/tree/roleId/name")
    public R getRoleByRoleId(@RequestParam("roleId") @NotNull(message = "roleId不能为空") Integer roleId) {
        return R.ok(sysMenuService.findMenuByRoleId(roleId)
                .stream()
                .map(MenuVO::getName)
                .collect(Collectors.toList()));
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @ApiOperation(value = "查询菜单的详细信息", notes = "通过ID查询菜单的详细信息")
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(sysMenuService.getById(id));
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @ApiOperation(value = "查询菜单的详细信息", notes = "通过ID查询菜单的详细信息")
    @GetMapping("/get")
    public R getByParamId(@RequestParam("id") @NotNull(message = "id不能为空") Integer id) {
        return R.ok(sysMenuService.getById(id));
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return success/false
     */
    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    @Log("新增菜单")
    @PostMapping
//    @PreAuthorize("hasAuthority('sys_menu_add')")
    public R save(@Valid @RequestBody SysMenu sysMenu) {
        return R.ok(sysMenuService.save(sysMenu));
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     */
    @ApiOperation(value = "删除菜单", notes = "通过id删除菜单")
    @Log("删除菜单")
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('sys_menu_del')")
    public R removeById(@PathVariable Integer id) {
        boolean result = sysMenuService.removeMenuById(id);
        if (result) {
            return R.ok("删除成功！");
        } else {
            return R.ok("由于菜单具有子菜单，删除失败！");
        }
    }


    /**
     * 更新菜单
     *
     * @param sysMenu
     * @return
     */
    @ApiOperation(value = "更新菜单", notes = "更新菜单")
    @Log("更新菜单")
    @PutMapping
    @PreAuthorize("hasAuthority('sys_menu_edit')")
    public R update(@Valid @RequestBody SysMenu sysMenu) {
        return R.ok(sysMenuService.updateMenuById(sysMenu));
    }

    /**
     * 分页查询角色信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询字典信息", notes = "分页查询字典信息")
    public R getRolePage(Page<SysMenu> page, QSysMenu sysMenu) {
        return R.ok(sysMenuService.page(page, CreateQuery.getQueryWrapper(sysMenu)));
    }

}
