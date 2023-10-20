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
public class QLowerRuleTask {


    /**
     * 指南id
     */
    @ApiModelProperty(value="指南id")
    @NotNull
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer guideId;
    /**
     * 部门id
     */
    @ApiModelProperty(value="部门id")
    @NotNull
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer departmentId;



    }
