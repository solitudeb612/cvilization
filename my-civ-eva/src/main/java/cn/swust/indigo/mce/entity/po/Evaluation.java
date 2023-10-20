package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@TableName("evaluation")
@ApiModel("规则根")
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "根规则Id")
    @TableId
    private Integer rootRuleId;

    @NotNull
    @ApiModelProperty(value ="根规则名称")
    private String ruleName;
}
