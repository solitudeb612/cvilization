package cn.swust.indigo.mce.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Binary Tree Cat
 */
@Data
@ApiModel(value = "数据概览")
public class DataVo {
    @ApiModelProperty(value = "根据最近的任务 还没有开始编辑报告的部门数量")
    private Integer absenteeismDepartmentCount;
    @ApiModelProperty(value = "根据最近的任务 报告为草稿状态的数量")
    private Integer draftReportCount;
    @ApiModelProperty(value = "根据最近的任务 报告为提交状态的数量")
    private Integer submitReportCount;
    @ApiModelProperty(value = "根据最近的任务 报告为驳回状态的数量")
    private Integer overruleReportCount;
    @ApiModelProperty(value = "任务的总数")
    private Integer guideTotalCount;
    @ApiModelProperty(value = "报告的总数")
    private Integer reportTotalCount;
}
