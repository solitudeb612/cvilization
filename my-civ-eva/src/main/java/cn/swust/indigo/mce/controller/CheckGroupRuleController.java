package cn.swust.indigo.mce.controller;

import cn.swust.indigo.mce.entity.vo.CheckGroupCriteria;
import cn.swust.indigo.mce.service.CheckGroupCriteriaService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.mce.entity.po.CheckGroupRule;
import cn.swust.indigo.mce.service.CheckGroupRuleService;
import cn.swust.indigo.mce.entity.vo.QCheckGroupRule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 自查组部门规则设置
 *
 * @author lhz
 * @date 2023-03-21 20:50:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/check/rule")
@Api(value = "check-group-rule", tags = "实地考察-站点类型检测规则项管理")
public class CheckGroupRuleController {

    private final CheckGroupRuleService checkGroupRuleService;
    private final CheckGroupCriteriaService checkGroupCriteriaService;


    /**
     * 根据组id查询规则
     * @param groupId
     * @return
     */
    @GetMapping("/group")
    @ApiOperation(value = "根据站点类型规则组id获取规则",notes = "根据站点类型规则组id获取规则")
    public R getByGroupId(@RequestParam("groupId")Integer groupId){
        return R.ok(checkGroupCriteriaService.list(groupId));
//        return R.ok(checkGroupRuleService.list(new LambdaQueryWrapper<CheckGroupRule>()
//                .eq(CheckGroupRule::getGroupId,groupId)));

    }

    /**
     * 分页查询
     * @param page           分页对象
     * @param checkGroupRule 自查组部门规则设置
     * @return
     */
    @ApiOperation(value = "分页查询站点类型检测规则", notes = "分页查询站点类型检测规则")
    @GetMapping("/page")
    public R getCheckGroupRulePage(Page<CheckGroupRule> page, QCheckGroupRule checkGroupRule) {
        return R.ok(checkGroupRuleService.page(page, CreateQuery.getQueryWrapper(checkGroupRule)));
    }

    /**
     * 通过id查询自查组部门规则设置
     * @param groupRuleId id
     * @return R
     */
    @ApiOperation(value = "通过站点类型检测规则id查询", notes = "通过站点类型检测规则id查询")
    @GetMapping("/get")
    public R getById(@RequestParam("groupRuleId") Integer groupRuleId) {
        //return R.ok(checkGroupRuleService.getById(groupRuleId));
        return R.ok(checkGroupRuleService.getRuleDetails(groupRuleId));

    }

    /**
     * 新增自查组部门规则设置
     * @param checkGroupRule 自查组部门规则设置
     * @return R
     */
    @ApiOperation(value = "新增站点类型检测规则", notes = "新增站点类型检测规则")
    @Log("新增自查组部门规则设置")
    @PostMapping
//    @PreAuthorize("hasAuthority('checkgrouprule_add')" )
    public R save(@RequestBody CheckGroupRule checkGroupRule) {
        boolean flag = checkGroupRuleService.save(checkGroupRule);
        return flag? R.ok(checkGroupRule.getGroupRuleId()) : R.failed("新增失败");
    }

    /**
     * 修改自查组部门规则设置
     * @param checkGroupCriteria 自查组部门规则设置
     * @return R
     */
    @ApiOperation(value = "修改站点类型检测规则", notes = "修改站点类型检测规则")
    @Log("修改自查组部门规则设置")
    @PutMapping
//    @PreAuthorize("hasAuthority('checkgrouprule_edit')" )
//    public R updateById(@RequestBody CheckGroupRule checkGroupRule) {
//        return R.ok(checkGroupRuleService.updateById(checkGroupRule));
//    }

    public R updateById(@RequestBody CheckGroupCriteria checkGroupCriteria) {
        return R.ok(checkGroupRuleService.updateById(checkGroupCriteria));
    }

    /**
     * 通过id删除自查组部门规则设置
     * @param groupRuleId
     * @return R
     */
    @ApiOperation(value = "通过id删除站点类型检测规则", notes = "通过id删除站点类型检测规则")
    @Log("通过id删除站点类型检测规则")
    @DeleteMapping("delete")
//    @PreAuthorize("hasAuthority('checkgrouprule_del')" )
    public R removeById(@RequestParam("groupRuleId") Integer groupRuleId) {
        return R.ok(checkGroupRuleService.removeById(groupRuleId));
    }

}
