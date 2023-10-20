package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 规则部门任务
 *
 * @author lhz
 * @date 2023-03-21 20:24:51
 */
@Data
@TableName("rule_dept_task")

@ApiModel(value = "规则部门任务")
public class RuleDeptTask {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    @ApiModelProperty(value = "")
    private Integer taskId;

    /**
     * 规则id
     */
    @ApiModelProperty(value = "规则id")
    private Integer ruleId;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer departmentId;

    /**
     * 任务详情
     */
    @Size(min = 1, max = 255, message = "任务详情 最大为255")
    @ApiModelProperty(value = "任务详情 长度: 1-255")
    private String taskDetail;

    /**
     * 是否是牵头部门
     */
    @ApiModelProperty(value = "是否是牵头部门")
    private Boolean leadFlag;

    /**
     * 根规则id
     */
    @ApiModelProperty(value = "根规则id")
    @NotNull
    private Integer rootRuleId;

}
