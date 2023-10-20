package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "更新部门规则任务")
public class RuleDeptTaskUpdate {

    /**
     * 任务id
     */
    @ApiModelProperty(value = "任务id")
    @NotNull
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer taskId;
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    @DynamicQuery(DynamicQuery.QType.eq)
    @NotNull
    private Integer departmentId;

    /**
     * 部门任务
     */
    @ApiModelProperty(value = "部门任务")
    private String taskDetail;
}
