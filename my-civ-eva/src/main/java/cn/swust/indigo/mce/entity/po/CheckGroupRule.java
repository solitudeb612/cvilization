package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 自查组部门规则设置
 *
 * @author lhz
 * @date 2023-03-21 20:50:21
 */
@Data
@TableName("check_group_rule")

@ApiModel(value = "自查组部门规则设置")
@AllArgsConstructor
@NoArgsConstructor
public class CheckGroupRule {
    private static final long serialVersionUID = 1L;

    /**
     * 自查类型规则id
     */
    @TableId(value = "group_rule_id", type = IdType.AUTO)
    @NotNull
    @ApiModelProperty(value = "自查类型规则id")
    private Integer groupRuleId;

    /**
     * 组id
     */
    @ApiModelProperty(value = "组id")
    private Integer groupId;

    /**
     * 测评标准名称
     */
    @ApiModelProperty(value = "测评标准名称")
    private String ruleName;

    /**
     * 测评标准明细
     */
    @ApiModelProperty(value = "测评标准明细")
    private String ruleDetail;

    /**
     * 问题序号
     */
    @ApiModelProperty(value = "问题序号")
    private Integer sort;

    /**
     * 权重
     */
    @ApiModelProperty(value = "权重")
    private Double weight;

    /**
     * 单笔分值
     */
    @ApiModelProperty(value = "单笔分值")
    private Double score;

    /**
     * 测评明细
     */
    @Size(min = 1, max = 255, message = "测评明细 最大为255")
    @ApiModelProperty(value = "测评明细 长度: 1-255")
    private String memo;

    /**
     * 标准笔数
     */
    @ApiModelProperty(value = "标准笔数")
    private Integer number;

}
