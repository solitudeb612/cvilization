package cn.swust.indigo.mce.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.po.SysUserRole;
import cn.swust.indigo.admin.service.SysDeptService;
import cn.swust.indigo.admin.service.SysUserRoleService;
import cn.swust.indigo.admin.service.SysUserService;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.log.annotation.Log;
import cn.swust.indigo.common.security.util.SecurityUtils;
import cn.swust.indigo.mce.entity.po.Guide;
import cn.swust.indigo.mce.entity.vo.AddCooperateDept;
import cn.swust.indigo.mce.entity.vo.QRuleDeptTask;
import cn.swust.indigo.mce.entity.vo.RuleDeptTaskUpdate;
import cn.swust.indigo.mce.entity.vo.RuleDeptTaskVo;
import cn.swust.indigo.mce.mapper.RuleDeptTaskMapper;
import cn.swust.indigo.mce.service.GuideService;
import cn.swust.indigo.mce.service.RuleDeptTaskService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 规则部门任务
 *
 * @author lhz
 * @date 2023-03-21 20:24:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/evaluation/dept" )
@Api(value = "rule-dept-task", tags = "网上申报规则部门任务管理")
public class RuleDeptTaskController {

    private final  RuleDeptTaskService ruleDeptTaskService;

    @Autowired
    SysDeptService sysDeptService;


    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    RuleDeptTaskMapper ruleDeptTaskMapper;

    @Autowired
    GuideService guideService;

//    /**
//     * 管理员-查询
//     * @param rootRuleId
//     * @return
//     */
//    @ApiOperation(value = "admin-查询所有任务", notes = "admin-查询所有任务")
//    @GetMapping("/admin/list" )
//    @Deprecated
//    public R getRuleDeptTaskPage(  @RequestParam("rootRuleId")Integer rootRuleId
//            ,@RequestParam(value = "ruleId",required = false)Integer ruleId) {
//        return R.ok(ruleDeptTaskService.listAll( rootRuleId,ruleId));
//    }

    @ApiOperation(value = "admin-牵头部门设置,没有牵头部门就新增或修改为牵头部门，有牵头部门则取消牵头部门",notes = "admin-牵头部门设置,没有牵头部门就新增或修改为牵头部门，有牵头部门则取消牵头部门")
    @PostMapping("/admin/set")
    public R setLeadDept(@Valid @RequestBody QRuleDeptTask ruleDeptTask){
        if(guideService.checkUseGuideCountGeTwo(ruleDeptTask.getRootRuleId())){
            return R.failed("该规则已经被使用两次！无法再次修改！");
        }

        Integer leadDept = ruleDeptTaskService.setLeadDept(ruleDeptTask);
        if(leadDept!=null){
            return R.ok(leadDept);
        }else{
            return R.failed(false);
        }
    }

    @GetMapping("/list")
//    @ApiOperation(value = "牵头部门-分页查询部门规则任务",notes = "牵头部门-分页查询部门规则任务")
    @Deprecated
    @ApiOperation(value = "牵头部门-分页查询部门规则任务",notes = "牵头部门-分页查询部门规则任务")
    public R getDeptRuleTask(@RequestParam("rootRuleId") Integer rootRuleId){
        //TODO: wangxiande 判断一下角色 把admin和牵头部门接口合并  返回跟这个部门有关的全部任务 牵头部门和协助部门
        return R.ok(ruleDeptTaskService.listInfo( rootRuleId));
    }

    /**
     * 通过id查询规则部门任务
     * @param taskId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/get" )
    public R getById(@RequestParam("taskId" ) Integer taskId) {
        return R.ok(ruleDeptTaskService.getById(taskId));
    }

//    /**
//     * 新增规则部门任务
//     * @param ruleDeptTask 规则部门任务
//     * @return R
//     */
//    //TODO:新增 admin
//    @ApiOperation(value = "新增规则部门任务", notes = "新增规则部门任务")
//    @Log("新增规则部门任务" )
//    @PostMapping("/set")
////    @PreAuthorize("hasAuthority('ruledepttask_add')" )
//    public R save(@RequestBody RuleDeptTask ruleDeptTask) {
//        boolean flag = ruleDeptTaskService.save(ruleDeptTask);
//        return flag? R.ok(ruleDeptTask.getTaskId()) : R.failed("新增失败");
//    }

    //添加配合部门
    //todo: taskId->ruleId
    @PostMapping("/add")
    @ApiOperation(value = "牵头部门-添加配合规则部门",notes = "牵头部门-添加配合规则部门")
    public R addCooperateDept(@RequestBody AddCooperateDept cooperateDept){
        if(guideService.checkUseGuideCountGeTwo(cooperateDept.getRootRuleId())){
            return R.failed("该规则已经被使用两次！无法再次修改！");
        }

        Integer save = ruleDeptTaskService.addCooperateDept(cooperateDept.getRuleId(), cooperateDept.getDepartmentId(), cooperateDept.getRootRuleId());
        if(save==null){
            return R.failed(false);
        }else if (save == -1){
            return R.failed("协助部门已经存在或者出现其它的错误！");
        } else{
            return R.ok(save);
        }
    }

    /**
     * 通过id删除规则部门任务
     * @param taskId
     * @return R
     */
    @ApiOperation(value = "通过id删除规则部门任务", notes = "通过id删除规则部门任务")
    @Log("通过id删除规则部门任务" )
    @DeleteMapping("/delete" )
//    @PreAuthorize("hasAuthority('ruledepttask_del')" )
    public R removeById(@RequestParam("taskId") Integer taskId) {
        return R.ok(ruleDeptTaskService.removeById(taskId));
    }


    /**
     * 修改规则部门任务
     *
     * @param qRuleDeptTaskUpdate 规则部门任务修改
     * @return R
     */
    @ApiOperation(value = "修改规则部门任务", notes = "修改规则部门任务")
    @Log("修改规则部门任务")
    @PutMapping("/task")
    //todo: RuleDeptTask-> 修改对象 id,task
//    @PreAuthorize("hasAuthority('ruledepttask_edit')" )
    public R updateById(@Valid @RequestBody RuleDeptTaskUpdate qRuleDeptTaskUpdate) {
        Integer rootRuleId = ruleDeptTaskService.getById(qRuleDeptTaskUpdate.getTaskId()).getRootRuleId();
        if(guideService.checkUseGuideCountGeTwo(rootRuleId)){
            return R.failed("指南已被使用两次及以上，无法修改");
        }
        boolean updateTaskById = ruleDeptTaskService.updateTaskById(qRuleDeptTaskUpdate);
        return updateTaskById ? R.ok(true) : R.failed(false);
    }

    @ApiOperation(value = "查询部门任务（条件、只返回牵头及协助部门）", notes = "通过rootRuleId和ruleId作为过滤条件获得所有的部门任务 type = null 全部 0 牵头 1 协助")
    @GetMapping("/list/info")
    public R listRuleDeptTaskVo(@RequestParam Integer rootRuleId, @RequestParam Integer ruleId,  @RequestParam(required = false) Integer type) {
        List<RuleDeptTaskVo> ruleDeptTaskVos = ruleDeptTaskService.listRuleDeptTaskVo(rootRuleId, ruleId, type);
        if (ObjectUtil.isNotNull(ruleDeptTaskVos)){
            return R.ok(ruleDeptTaskVos);
        }else {
            return R.failed("数据不存在！");
        }
    }

    @ApiOperation(value = "查询部门任务（根据当前用户获取）", notes = "返回当前用户相关的所有部门任务，如果是牵头部门领导，则附加返回协助部门任务，反之，一样；如果当前用户不为部门管理员则返回错误")
    @GetMapping("/list/info/user")
    public R listRuleDeptTaskVoByUser(@RequestParam Integer rootRuleId) {
        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();
        SysDept deptByUserId = sysDeptService.getDeptByUserId(userId);
        if (ObjectUtil.isNull(deptByUserId)) {
            SysUser user = sysUserService.getById(userId);
            if (user.getUserId() != null) {
                LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(SysUserRole::getUserId, userId);
                SysUserRole role = sysUserRoleService.getOne(wrapper);
                if (role.getRoleId() == 1){
                    // 如果是管理员
                    return R.ok(ruleDeptTaskService.listRuleDeptTaskVoByUser(rootRuleId,null));
                } else {
                    return R.failed("错误！您不是超级管理员或者部门管理员！");
                }
            } else {
                return R.failed("错误，当前用户不是部门管理员！");
            }
        } else{
//            return R.ok(ruleDeptTaskService.listRuleDeptTaskVoByUser(rootRuleId,deptByUserId.getDeptId()));
            return R.ok(ruleDeptTaskService.listRuleDeptTaskVoByUserAll(rootRuleId,deptByUserId.getDeptId()));
        }
    }

    @GetMapping("/lead/depts")
    @ApiOperation(value = "获取所有牵头部门", notes = "管理员")
    public R getAllLeadDepartment(@RequestParam Integer rootRuleId, @RequestParam Integer ruleId){
        return R.ok(ruleDeptTaskMapper.getAllLeadDepartment(rootRuleId, ruleId));
    }
}
