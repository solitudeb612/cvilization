package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author lhz
 */
@Data
@ApiModel(value = "网上申报提交查询条件")
@AllArgsConstructor
@NoArgsConstructor
public class QCommitDetail {
    /**
     * 规则id
     */
    @ApiModelProperty(value = "规则id")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer ruleId;

    /**
     * 指南id
     */
    @ApiModelProperty(value = "指南id")
    @NotNull
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer guideId;

}
