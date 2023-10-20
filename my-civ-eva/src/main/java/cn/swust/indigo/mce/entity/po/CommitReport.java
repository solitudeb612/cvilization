package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author Binary cat
 * @date 2023/3/23 14:27
 */
@Data
@TableName("commit_report")
public class CommitReport {

    /**
     * 分组的组号 ID
     */
    @TableId(value = "report_id",type = IdType.AUTO)
    private Integer reportId;

    /**
     * 创建者 ID
     */
    @TableField("creator_id")
    private Integer creatorId;

    /**
     * 提交地点
     */
    @ApiModelProperty(value = "提交地点")
    private String address;

    /**
     * 规则id
     */
    @TableField("rule_id")
    @ApiModelProperty(value = "规则id")
    private Integer ruleId;


    /**
     * 指南id
     */
    @TableField("guide_id")
    @ApiModelProperty(value = "指南id")
    @NotNull
    private Integer guideId;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private LocalDateTime commitTime;
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    @TableField("department_id")
    private Integer departmentId;


    /**
     * 提交设备
     */
    @Size(min = 1, max = 255, message = "提交设备 最大为255")
    @ApiModelProperty(value = "提交设备 长度: 1-255")
    @TableField("commit_device")
    private String commitDevice;

    @ApiModelProperty(value = "分数")
    private int grade;

    @ApiModelProperty(value = "0.草稿 1.提交 2.通过 3.牵头部门驳回 4.抽查驳回")
    private int status;

    @ApiModelProperty(value = "评审部门id")
    private Integer reviewDeptId;

    /**
     * 评审意见详情
     */
    @Size(min = 1, max = 255, message = "评审意见详情 最大为255")
    @ApiModelProperty(value = "评审意见详情 长度: 1-255")
    @TableField("review_info")
    private String reviewInfo;

}
