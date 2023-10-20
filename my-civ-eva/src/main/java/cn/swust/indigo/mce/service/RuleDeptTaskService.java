package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.po.RuleDeptTask;
import cn.swust.indigo.mce.entity.vo.QRuleDeptTask;
import cn.swust.indigo.mce.entity.vo.RuleDeptTaskUpdate;
import cn.swust.indigo.mce.entity.vo.RuleDeptTaskVo;
import cn.swust.indigo.mce.entity.vo.TaskCheckCommit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 规则部门任务
 *
 * @author lhz
 * @date 2023-03-21 20:24:51
 */
public interface RuleDeptTaskService extends IService<RuleDeptTask> {
    List<RuleDeptTaskVo> listInfo(Integer rootRuleId);

    Integer addCooperateDept(Integer ruleId,Integer deptId,Integer rootRuleId);

    boolean updateTaskById(RuleDeptTaskUpdate qRuleDeptTaskUpdate);

    Integer setLeadDept(QRuleDeptTask qRuleDeptTask);

    List<RuleDeptTaskVo> listAll(Integer rootRuleId,Integer ruleId);

    List<RuleDeptTaskVo> listRuleDeptTaskVo(Integer rootRuleId,Integer ruleId, Integer type);

    List<RuleDeptTaskVo> listRuleDeptTaskVoByUser(Integer rootRuleId,Integer userDeptId);

    /**
     * 获取当前用户对应部门的为牵头部门的所有rdt和为协助部门的rdt
     * @param rootRuleId
     * @param userDeptId
     * @return
     */
    List<RuleDeptTaskVo> listRuleDeptTaskVoByUserAll(Integer rootRuleId,Integer userDeptId);

    List<TaskCheckCommit> selectTaskCommits(Integer rootRuleId);
}
