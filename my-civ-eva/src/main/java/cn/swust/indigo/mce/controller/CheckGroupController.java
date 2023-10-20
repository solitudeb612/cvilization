package cn.swust.indigo.mce.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.mce.entity.po.CheckGroup;
import cn.swust.indigo.mce.entity.vo.QCheckGroup;
import cn.swust.indigo.mce.service.CheckGroupRuleService;
import cn.swust.indigo.mce.service.CheckGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/check/group")
@AllArgsConstructor
@Api(value = "check-group", tags = "实地考察-站点类型规则组")
public class CheckGroupController {

    @Resource
    private CheckGroupService checkGroupService;
    @Resource
    private CheckGroupRuleService checkGroupRuleService;

    @GetMapping("/all")
    @ApiOperation(value = "获取所有实地考察-站点类型规则组", notes = "获取所有实地考察-站点类型规则组")
    public R getAll() {
        return R.ok(checkGroupService.list());
    }

    @GetMapping("/list")
    @ApiOperation(value = "根据拼音模糊查询实地考察-站点类型规则组", notes = "根据拼音模糊查询实地考察-站点类型规则组")
    public R getGroupCheckByPy(@RequestParam("py") String py) {
        List<CheckGroup> checkGroups = checkGroupService.getGroupChecksByPy(py);
        return R.ok(checkGroups);
    }

    @GetMapping("get")
    @ApiOperation(value = "根据id查询实地考察-站点类型规则组", notes = "根据id查询实地考察-站点类型规则组")
    public R getById(@RequestParam("checkGroupId") Integer checkGroupId) {
        return R.ok(checkGroupService.getById(checkGroupId));
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "根据id删除实地考察-站点类型规则组", notes = "根据id删除实地考察-站点类型规则组")
    public R deleteById(@RequestParam("checkGroupId") Integer checkGroupId) {
        return R.ok(checkGroupService.removeById(checkGroupId));
    }

    @PutMapping("update")
    @ApiOperation(value = "修改实地考察-站点类型规则组", notes = "修改实地考察-站点类型规则组")
    public R putCheckGroup(@RequestBody QCheckGroup qCheckGroup) {
        return R.ok(checkGroupService.updateCheckGroup(qCheckGroup));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增实地考察-站点类型规则组", notes = "新增实地考察-站点类型规则组")
    public R add(@RequestBody CheckGroup checkGroup) {
        boolean flag = checkGroupService.save(checkGroup);
        return flag? R.ok(checkGroup.getCheckGroupId()) : R.failed("新增失败");
    }

}
