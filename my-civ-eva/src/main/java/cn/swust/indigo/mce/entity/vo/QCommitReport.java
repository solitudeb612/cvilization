package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QCommitReport {

    /**
     * 规则id
     */
    @ApiModelProperty(value="规则id")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer ruleId;

    /**
     * 指南id
     */
    @ApiModelProperty(value="指南id")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer guideId;

    /**
     * 部门id
     */
    @ApiModelProperty(value="部门id")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer departmentId;

}
