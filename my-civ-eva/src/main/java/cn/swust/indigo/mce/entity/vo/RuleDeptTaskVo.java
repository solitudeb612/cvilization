package cn.swust.indigo.mce.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleDeptTaskVo   {
    private Integer taskId;
    private Integer ruleLevel1Id;
    private Integer ruleLevel2Id;
    private Integer ruleLevel3Id;
    private Integer ruleLevel4Id;
    private Integer rootRuleId;
    private Integer ruleId;
    private String taskDetail;
    private String ruleLevel1Name;
    private String ruleLevel2Name;
    private String ruleLevel3Name;
    private String ruleLevel4Name;
    private String departmentName;
    private Integer departmentId;
    private Boolean leadFlag;

    /**
     * 管理员：这个 RULE 是否设置了牵头部门 ； 牵头部门管理员：这个 RULE 下面是否设置了协助部门
     */
    private Boolean hasDepartment;

    /**
     * 管理员：牵头部门是否设置了任务提交条目 ； 牵头部门管理员：相关的协助部门是否都设置了任务提交条目
     */
    private Boolean hasTaskCommit;
}
