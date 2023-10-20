package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import cn.swust.indigo.mce.utils.NameToPinyinUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "查询指南规则")
public class QEvaluationRules {

    @NotNull
    @ApiModelProperty(value = "根规则id")
    private Integer rootRuleId;
}
