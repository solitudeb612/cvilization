package cn.swust.indigo.mce.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.po.SysUserRole;
import cn.swust.indigo.admin.mapper.SysDeptMapper;
import cn.swust.indigo.admin.service.SysUserRoleService;
import cn.swust.indigo.common.security.util.SecurityUtils;
import cn.swust.indigo.mce.entity.po.RuleDeptTask;
import cn.swust.indigo.mce.entity.po.TaskCommit;
import cn.swust.indigo.mce.entity.vo.*;
import cn.swust.indigo.mce.mapper.EvaluationRulesMapper;
import cn.swust.indigo.mce.mapper.RuleDeptTaskMapper;
import cn.swust.indigo.mce.mapper.TaskCommitMapper;
import cn.swust.indigo.mce.mapper.UserMapper;
import cn.swust.indigo.mce.service.RuleDeptTaskService;
import cn.swust.indigo.mce.service.TaskCommitService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 规则部门任务
 *
 * @author lhz
 * @date 2023-03-21 20:24:51
 */
@Service
@AllArgsConstructor
public class RuleDeptTaskServiceImpl extends ServiceImpl<RuleDeptTaskMapper, RuleDeptTask> implements RuleDeptTaskService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private TaskCommitMapper taskCommitMapper;

    @Autowired
    private EvaluationRulesMapper evaluationRulesMapper;
    @Autowired
    private RuleDeptTaskMapper ruleDeptTaskMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public List<RuleDeptTaskVo>
    listInfo(Integer rootRuleId) {
        SysUser sysUser = SecurityUtils.getUser().getSysUser();
        UserVo userVo = userMapper.selectById(sysUser.getUserId());
        Integer departmentId = userMapper.selectById(userVo.getUserId()).getDepartmentId();
        //获取所有对应牵头部门
        List<RuleDeptTask> ruleDeptTaskList = this.list(new LambdaQueryWrapper<RuleDeptTask>()
                .eq(RuleDeptTask::getDepartmentId, departmentId)
                .eq(RuleDeptTask::getRootRuleId, rootRuleId)
                .eq(RuleDeptTask::getLeadFlag, true));
        //牵头部门id
        List<Integer> deptIds = Lists.transform(ruleDeptTaskList, RuleDeptTask::getDepartmentId);
        if (deptIds.isEmpty()) {
            return null;
        }
        //牵头部门对应的规则id
        List<Integer> ruleIds = Lists.transform(ruleDeptTaskList, RuleDeptTask::getRuleId);
        if (ruleIds.isEmpty()) {
            return null;
        }
        List<RuleDeptTaskVo> deptTaskPage = getListDetails(rootRuleId, deptIds, ruleIds);
        return deptTaskPage;
    }


    public List<RuleDeptTaskVo> getListDetails(Integer rootRuleId
            , List<Integer> deptIds, List<Integer> ruleIds) {
        //索取部门对应规则id下的所有task
        List<RuleDeptTaskVo> ruleDeptTaskVos = ruleDeptTaskMapper.listInfoRuleTask(ruleIds);
        //获取规则四级结构
        List<QEvaluationTableInfo> ruleInfo = evaluationRulesMapper.listAllInfoRules(ruleIds, rootRuleId);
        for (RuleDeptTaskVo ruleDeptTaskVoInfo : ruleDeptTaskVos) {
            for (QEvaluationTableInfo rule : ruleInfo) {
                if (rule.getRuleLevel4Id().equals(ruleDeptTaskVoInfo.getRuleId())) {
                    ruleDeptTaskVoInfo.setRuleLevel4Id(rule.getRuleLevel4Id());
                    ruleDeptTaskVoInfo.setRuleLevel3Id(rule.getRuleLevel3Id());
                    ruleDeptTaskVoInfo.setRuleLevel2Id(rule.getRuleLevel2Id());
                    ruleDeptTaskVoInfo.setRuleLevel1Id(rule.getRuleLevel1Id());
                    ruleDeptTaskVoInfo.setRuleLevel4Name(rule.getRuleLevel4Name());
                    ruleDeptTaskVoInfo.setRuleLevel3Name(rule.getRuleLevel3Name());
                    ruleDeptTaskVoInfo.setRuleLevel2Name(rule.getRuleLevel2Name());
                    ruleDeptTaskVoInfo.setRuleLevel1Name(rule.getRuleLevel1Name());
                    ruleDeptTaskVoInfo.setLeadFlag(deptIds.contains(ruleDeptTaskVoInfo.getDepartmentId()));
                    break;
                }
            }
        }
        List<SysDept> sysDepts = sysDeptMapper.selectBatchIds(deptIds);
        for (RuleDeptTaskVo ruleDeptTaskVoInfo : ruleDeptTaskVos) {
            for (SysDept sysDept : sysDepts) {
                if (sysDept.getDeptId().equals(ruleDeptTaskVoInfo.getDepartmentId())) {
                    ruleDeptTaskVoInfo.setDepartmentName(sysDept.getName());
                    break;
                }
            }
        }

        return ruleDeptTaskVos;
    }

    @Override
    public Integer addCooperateDept(Integer ruleId, Integer deptId, Integer rootRuleId) {
        RuleDeptTask ruleDeptTask = new RuleDeptTask();
        ruleDeptTask.setDepartmentId(deptId);
        ruleDeptTask.setRuleId(ruleId);
        ruleDeptTask.setRootRuleId(rootRuleId);
        ruleDeptTask.setLeadFlag(false);

        // 判断是否存在相同的
        LambdaQueryWrapper<RuleDeptTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RuleDeptTask::getRootRuleId, rootRuleId)
                .eq(RuleDeptTask::getDepartmentId, deptId)
                .eq(RuleDeptTask::getRuleId, ruleId);

        if (ruleDeptTaskMapper.selectOne(wrapper) != null) {
            return -1;
        }

        boolean save = this.save(ruleDeptTask);
        if (save) {
            return ruleDeptTask.getTaskId();
        } else {
            return null;
        }
    }

    @Override
    //ruleId可以为空
    public boolean updateTaskById(RuleDeptTaskUpdate ruleDeptTaskUpdate) {
        UserInfo userInfo = SecurityUtils.getUser();
        if (userInfo == null) {
            return false;
        }
//        RuleDeptTask deptTask = this.getOne(new LambdaQueryWrapper<RuleDeptTask>().eq(RuleDeptTask::getTaskId, ruleDeptTaskUpdate.getTaskId()));
//        deptTask.setTaskDetail(ruleDeptTaskUpdate.getTaskDetail() );
        return this.baseMapper.updateTaskDetailById(ruleDeptTaskUpdate.getTaskId(), ruleDeptTaskUpdate.getTaskDetail()) > 0 ? true : false;
    }

    @Override
    @Transactional
    public Integer setLeadDept(QRuleDeptTask qRuleDeptTask) {

        RuleDeptTask leaderRuleDeptTask = this.getOne(new LambdaQueryWrapper<RuleDeptTask>()
                .eq(RuleDeptTask::getRuleId, qRuleDeptTask.getRuleId())
                .eq(RuleDeptTask::getLeadFlag, true)
                .eq(RuleDeptTask::getRootRuleId, qRuleDeptTask.getRootRuleId()));

        if (leaderRuleDeptTask != null) {
            this.removeById(leaderRuleDeptTask.getTaskId());
        }

        //根据规则Id以及部门id查询部门规则任务是否有存在
        RuleDeptTask ruleDeptTask = this.getOne(new LambdaQueryWrapper<RuleDeptTask>()
                .eq(RuleDeptTask::getRuleId, qRuleDeptTask.getRuleId())
                .eq(RuleDeptTask::getLeadFlag, qRuleDeptTask.getDepartmentId())
                .eq(RuleDeptTask::getRootRuleId, qRuleDeptTask.getRootRuleId()));

        //存在，修改 lead_flag
        if (ruleDeptTask != null) {
            ruleDeptTask.setLeadFlag(!ruleDeptTask.getLeadFlag());
            boolean updateById = this.updateById(ruleDeptTask);
            return updateById ? ruleDeptTask.getTaskId() : null;
        }

        //不存在，新增牵头部门
        RuleDeptTask setRuleDeptTask = new RuleDeptTask();
        setRuleDeptTask.setRuleId(qRuleDeptTask.getRuleId());
        setRuleDeptTask.setRootRuleId(qRuleDeptTask.getRootRuleId());
        setRuleDeptTask.setDepartmentId(qRuleDeptTask.getDepartmentId());
        setRuleDeptTask.setTaskDetail(qRuleDeptTask.getTaskDetail());
        setRuleDeptTask.setLeadFlag(true);

        boolean save = this.save(setRuleDeptTask);
        return save ? setRuleDeptTask.getTaskId() : null;
    }

    @Override
    public List<RuleDeptTaskVo> listAll(Integer rootRuleId, Integer ruleId) {
        List<RuleDeptTask> list = null;
        if (ruleId == null) {
            list = this.list(new LambdaQueryWrapper<RuleDeptTask>().eq(RuleDeptTask::getRootRuleId, rootRuleId));
        } else {
            list = this.list(new LambdaQueryWrapper<RuleDeptTask>()
                    .eq(RuleDeptTask::getRootRuleId, rootRuleId)
                    .eq(RuleDeptTask::getRuleId, ruleId));
        }
        //部门id
        List<Integer> deptIds = Lists.transform(list, RuleDeptTask::getDepartmentId);
        //部门对应的规则id
        List<Integer> ruleIds = Lists.transform(list, RuleDeptTask::getRuleId);
        List<RuleDeptTaskVo> pageDetails = getListDetails(rootRuleId, deptIds, ruleIds);
        return pageDetails;
    }

    @Override
    public List<RuleDeptTaskVo> listRuleDeptTaskVo(Integer rootRuleId, Integer ruleId, Integer type) {
        List<RuleDeptTaskVo> vos = ruleDeptTaskMapper.listRuleDeptTaskVo(rootRuleId, ruleId);
        if (type == null) {
            return vos;
        } else {
            if (type == 0) {
                return vos.stream().filter(item -> (true == item.getLeadFlag())).collect(Collectors.toList());
            } else {
                return vos.stream().filter(item -> (false == item.getLeadFlag())).collect(Collectors.toList());
            }
        }
    }

    @Override
    public List<RuleDeptTaskVo> listRuleDeptTaskVoByUser(Integer rootRuleId, Integer userDeptId) {
        return refine(ruleDeptTaskMapper.listRuleDeptTaskVoByUser(rootRuleId, userDeptId));
    }

    @Override
    public List<RuleDeptTaskVo> listRuleDeptTaskVoByUserAll(Integer rootRuleId, Integer userDeptId) {
        return refine(ruleDeptTaskMapper.listRuleDeptTaskVoByUserAll(rootRuleId, userDeptId));
    }

    @Override
    public List<TaskCheckCommit> selectTaskCommits(Integer rootRuleId) {
        return ruleDeptTaskMapper.selectTaskCommits(rootRuleId);
    }

    /**
     * 给RULE DEPT TASK VO 增加 HAS TASK COMMIT 和 HAS DEPT 属性
     * @param ruleDeptTaskVos
     * @return
     */
    private List<RuleDeptTaskVo> refine(List<RuleDeptTaskVo> ruleDeptTaskVos) {
        // 检查角色
        Integer userId = SecurityUtils.getUser().getSysUser().getUserId();
        LambdaQueryWrapper<SysUserRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.eq(SysUserRole::getUserId, userId);
        Integer roleId = sysUserRoleService.getOne(roleWrapper).getRoleId();

        // 获取存在 TASK COMMIT 的 RULE DEPT TASK ID
        Set<Integer> set = ruleDeptTaskVos.stream().map(RuleDeptTaskVo::getTaskId).collect(Collectors.toSet());
        LambdaQueryWrapper<TaskCommit> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNull(set) || set.isEmpty()){
            return ruleDeptTaskVos;
        }
        wrapper.in(TaskCommit::getTaskId, set);
        Set<Integer> taskIdSet = taskCommitMapper.selectList(wrapper).stream().map(TaskCommit::getTaskId).collect(Collectors.toSet());

        if(roleId == 1){
            // ADMIN
            for (RuleDeptTaskVo item : ruleDeptTaskVos) {
                // 初始化
                item.setHasDepartment(false);
                item.setHasTaskCommit(false);
            }

            for (RuleDeptTaskVo item : ruleDeptTaskVos) {
                if (ObjectUtil.isNull(item.getTaskId())){
                    item.setHasTaskCommit(false);
                    item.setHasDepartment(false);
                }
                // 如果部门存在 TASK COMMIT
                if (ObjectUtil.isNotNull(item.getTaskId()) && taskIdSet.contains(item.getTaskId())){
                    item.setHasTaskCommit(true);
                }
                if (ObjectUtil.isNotNull(item.getLeadFlag()) && item.getLeadFlag()){
                    for (RuleDeptTaskVo temp : ruleDeptTaskVos) {
                        if (item.getRuleId().equals(temp.getRuleId()) && item.getRootRuleId().equals(temp.getRootRuleId())){
                            temp.setHasDepartment(true);
                        }
                    }
                }
            }
        } else {
            for (RuleDeptTaskVo item : ruleDeptTaskVos) {
                // 初始化
                item.setHasTaskCommit(true);
                item.setHasDepartment(false);
            }

            // 检查每一个协助部门是否设置了任务
            for(RuleDeptTaskVo item : ruleDeptTaskVos){
                if (item.getLeadFlag()){
                    int assistCount = 0;
                    for (RuleDeptTaskVo temp : ruleDeptTaskVos) {
                        if (ObjectUtil.isNotNull(item.getDepartmentId())
                                && item.getRuleId().equals(temp.getRuleId())
                                && item.getDepartmentId() != temp.getDepartmentId()
                                && item.getRootRuleId().equals(temp.getRootRuleId())){
                            if (ObjectUtil.isNull(item.getTaskId())){
                                item.setHasTaskCommit(false);
                                item.setHasDepartment(false);
                            }
                            assistCount ++;
                            // 规则 部门id has task
                            if(ObjectUtil.isNotNull(item.getTaskId()) && !taskIdSet.contains(temp.getTaskId())){
                                // 如果有一个协助部门没有设置任务
                                item.setHasTaskCommit(false);
                            }
                            item.setHasDepartment(true);
                        }
                    }
                    if (assistCount == 0){
                        item.setHasTaskCommit(true);
                    }
                }
            }
        }

        return ruleDeptTaskVos;
    }
}
