package cn.swust.indigo.mce.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 在线申报提交要求
 *
 * @author lhz
 * @date 2023-03-23 10:37:51
 */
@Data

public class TaskCheckCommit {
    private static final long serialVersionUID = 1L;

    /**
     * 任务提交数量
     */
    @ApiModelProperty(value = "任务提交数量")
    private Integer commitCount;

    /**
     * 任务id
     */
    @ApiModelProperty(value = "任务id")
    private Integer taskId;

    /**
     * 部门名称
     */
    @Size(min = 1, max = 255, message = "部门名称 最大为255")
    @ApiModelProperty(value = "部门名称 长度: 1-255")
    private String departmentName;
}
