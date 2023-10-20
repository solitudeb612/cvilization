package cn.swust.indigo.mce.entity.po;

import cn.swust.indigo.mce.utils.NameToPinyinUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评判规则
 *
 * @author lhz
 * @date 2023-02-24 16:57:29
 */
@Data
@TableName("evaluation_rules")
@ApiModel(value = "评判规则")
public class EvaluationRules implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 规则id
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    @ApiModelProperty(value = "规则id")
    private Integer ruleId;

    /**
     * 上级规则id
     */
    @ApiModelProperty(value = "上级规则id")
    @NotNull
    private Integer parentId;

    /**
     * 规则名称
     */
    @Size(min = 1, max = 100, message = "规则名称 最大为100")
    @ApiModelProperty(value = "规则名称 长度: 1-100")
    @NotNull
    private String ruleName;

    public String setRulePy() {
        if(StringUtils.isEmpty(rulePy)){
            String rule = ruleName;
            if (ruleName.length() >10){
                rule = ruleName.substring(0,10);
            }
            this.rulePy= NameToPinyinUtil.getPinyinFirst(rule);
        }
        return rulePy;
    }



    /**
     * 规则名拼音
     */
    @Size(min = 1, max = 100, message = "规则名拼音 最大为100")
    @ApiModelProperty(value = "规则名拼音 长度: 1-100")
//    @NotNull
    private String rulePy;



    /**
     * 是否提交材料 0未提交 1已提交
     */
    @NotNull
    @ApiModelProperty(value = "是否提交材料 0未提交 1已提交")
    private Boolean submit;

    /**
     * 满分
     */
//    @NotNull
    @ApiModelProperty(value = "满分")
    private Double fullMarks;

    /**
     * 一票否决 0不是一票否决 1，一票否决
     */
    @NotNull
    @Size(min = 1, max = 1, message = "一票否决 0不是一票否决 1，一票否决 最大为1")
    @ApiModelProperty(value = "一票否决 0不是一票否决 1，一票否决 长度: 1-1")
    private Integer veto;

    /**
     * 排序
     */
    @NotNull
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 规则根id
     */
    @NotNull
    @ApiModelProperty(value = "根规则Id")
    private Integer rootRuleId;
    /**
     *
     */
    @ApiModelProperty("规则等级")
    private Integer level;
}
