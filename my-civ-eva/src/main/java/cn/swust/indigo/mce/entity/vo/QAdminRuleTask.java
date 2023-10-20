package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author lhz
 */
@Data
@ApiModel(value = "网上申报提交查询条件")
public class QAdminRuleTask {

    /**
     * 指南id
     */
    @ApiModelProperty(value = "指南id")
    @NotNull
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer guideId;


    /**
     * departmentId
     */
    @ApiModelProperty(value = "部门id")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer departmentId;

    /**
     * 是否是admin
     */
    @NotNull
    @ApiModelProperty(value = "是否是admin")
    private boolean isAdmin = false;


    @ApiModelProperty(value = "1.提交 2.通过 3.牵头部门驳回 4.抽查驳回")
    private Integer status;
}
