package cn.swust.indigo.admin.controller;

import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.vo.QSysDept;
import cn.swust.indigo.admin.entity.vo.SystemDepartmentType;
import cn.swust.indigo.admin.service.SysDeptService;
import cn.swust.indigo.admin.service.impl.SysDeptServiceImpl;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/dept")
@Api(value = "dept", tags = "部门管理模块")
public class SysDeptController {
    @Autowired
    SysDeptService sysDeptService;

    @Autowired
    SysDeptServiceImpl sysDeptServiceImpl;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysDept
     */
    @ApiOperation(value = "查询", notes = "通过ID查询")
    @GetMapping("/get")
    public R getById(@RequestParam("deptId") Integer id) {
        return R.ok(sysDeptService.getById(id));
    }


    /**
     * 返回所有部门树形菜单集合
     *
     * @return 树形菜单
     */
    @ApiOperation(value = "返回所有部门树形菜单集合", notes = "返回所有部门树形菜单集合")
    @GetMapping(value = "/tree")
    public R getTree() {
        return R.ok(sysDeptService.selectTreeByType(SystemDepartmentType.ALL));
    }

    /**
     * 返回被此user管理的所有部门，及其以下的部门
     *
     * @return 树形菜单
     */
    @ApiOperation(value = "返回被此user管理的所有部门，及其以下的部门", notes = "返回被此user管理的所有部门，及其以下的部门")
    @GetMapping(value = "/treeById")
    public R getTreeById(@RequestParam("user_id") Integer user_id) {
        return R.ok(sysDeptService.selectTreeById(user_id));
    }

    @ApiOperation(value = "根据选择类型返回所属树形菜单集合 type = 1 为区域， type 2 行政", notes = "根据选择类型返回所属树形菜单集合 0:全部")
    @GetMapping(value = "/tree/type")
    public R getTreeByType(@RequestParam("department_type") Integer department_type) {
        if (department_type < 0 || department_type > 3) {
            return R.failed("不合法的类型");
        }
        return R.ok(sysDeptService.selectTreeByType(department_type));
    }

    @ApiOperation(value = "新增一个部门", notes = "添加部门，如果成功返回部门的Id")
    @Log("添加部门")
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('sys_dept_add')")
    public R save(@Valid @RequestBody SysDept sysDept) {
        Boolean flag = sysDeptService.saveDept(sysDept);
        if (flag) {
            // 成功则返回新增的部门ID
            HashMap<String, Integer> map = new HashMap<String, Integer>() {{
                put("DepartmentId", sysDept.getDeptId());
            }};
            return R.ok(map);
        } else {
            return R.failed("添加失败，请重新处理！");
        }
    }

    /**
     * 删除
     *
     * @param departmentId ID
     * @return success/false
     */
    @ApiOperation(value = "根据部门ID删除部门(删除其部门和其子部门)", notes = "根据部门ID删除部门(删除其部门和其子部门)")
    @Log("删除部门")
    @DeleteMapping("/del")
//    @PreAuthorize("hasAuthority('sys_dept_del')")
    public R removeById(@RequestParam("departId") Integer departmentId) {
        return R.ok(sysDeptService.removeDeptById(departmentId));
    }

    @ApiOperation(value = "根据部门ID删除部门(不使用关系表)", notes = "根据部门ID删除部门(不使用关系表)")
    @Log("删除部门")
    @DeleteMapping("/delete")
    public R removeByIdAndSubTree(@RequestParam("departId") Integer departmentId) {
        return R.ok(sysDeptService.removeByIdAndSubTree(departmentId));
    }

    /**
     * 编辑
     *
     * @param sysDept 实体
     * @return success/false
     */
    @ApiOperation(value = "编辑部门", notes = "编辑部门")
    @Log("编辑部门")
    @PutMapping("/edit")
//    @PreAuthorize("hasAuthority('sys_dept_edit')")
    public R update(@Valid @RequestBody SysDept sysDept) {
        sysDept.setUpdateTime(LocalDateTime.now());
        boolean flag = sysDeptService.updateDeptById(sysDept);
        if (flag) {
            return R.ok();
        } else {
            return R.failed("领导已有管辖部门，请重新修改！");
        }
    }

    /**
     * 简单分页查询
     *
     * @param qSysDept 系统终端
     * @return ok
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R list(QSysDept qSysDept) {
        return R.ok(sysDeptService.selectPage(qSysDept));
    }


    @ApiOperation(value = "设置部门的的LeaderId", notes = "设置部门的的LeaderId")
    @PutMapping("/setLeader")
    public R setDepartmentLeader(@RequestParam("userId") Integer userId, @RequestParam("departmentId") Integer departmentId) {
        // TODO zhangjia 限定一个人只能管理一个部门 excel id = 4
        boolean flag = sysDeptService.setDepartmentLeader(userId, departmentId);
        if (flag) {
            return R.ok();
        } else {
            return R.failed("领导已有管辖部门，请重新修改！");
        }
    }
}
