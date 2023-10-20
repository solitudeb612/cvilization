package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.RuleDeptTask;
import cn.swust.indigo.mce.entity.vo.CommitRuleTableInfo;
import cn.swust.indigo.mce.entity.vo.CommitRuleTableInfoVo;
import cn.swust.indigo.mce.entity.vo.RuleDeptTaskVo;
import cn.swust.indigo.mce.entity.vo.TaskCheckCommit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 规则部门任务 Mapper 接口
 *
 * @author lhz
 * @date 2023-03-21 20:24:51
 */
@Mapper
public interface RuleDeptTaskMapper extends BaseMapper<RuleDeptTask> {

    List<CommitRuleTableInfo> commitRuleList( @Param("userId") Integer userId, @Param("rootId") Integer rootId);

    List<RuleDeptTask> getLeadDepartment(Integer userId);


    List<CommitRuleTableInfoVo> getAllSubordinateDepartments(
            @Param("rootRuleId") Integer rootRuleId
            ,@Param("guideId") long guideId
            ,@Param("index") long index
            ,@Param("size") long size
            ,@Param("status") Integer status);



    List<RuleDeptTaskVo> listInfoRuleTask(@Param("list") List<Integer> ruleIds);

    List<RuleDeptTaskVo> listRuleDeptTaskVo(@Param("rootRuleId")Integer rootRuleId, @Param("ruleId")Integer ruleId);

    List<RuleDeptTaskVo> listRuleDeptTaskVoByUser(@Param("rootRuleId")Integer rootRuleId, @Param("userDeptId")Integer userDeptId);

    List<CommitRuleTableInfo> listCommitRuleTableInfo(@Param("rootRuleId")Integer rootRuleId,@Param("departmentId")Integer departmentId,
                                                      @Param("ruleId")Integer ruleId,  @Param("guideId")Integer guideId);

    List<RuleDeptTaskVo>  listRuleDeptTaskVoByUserAll(@Param("rootRuleId")Integer rootRuleId, @Param("userDeptId")Integer userDeptId);

    List<RuleDeptTaskVo> getAllLeadDepartment(@Param("rootRuleId")Integer rootRuleId,@Param("ruleId")Integer ruleId);

    @Update("update rule_dept_task set task_detail = #{detail} where task_id = #{taskId}")
    int updateTaskDetailById(@Param("taskId")Integer taskId,@Param("detail")String detail);


    List<TaskCheckCommit> selectTaskCommits(@Param("rootRuleId") Integer rootRuleId);
}
