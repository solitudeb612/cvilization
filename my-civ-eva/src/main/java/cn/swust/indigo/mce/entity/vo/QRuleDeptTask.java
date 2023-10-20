package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "添加部门任务或设置牵头部门规则任务")
public class QRuleDeptTask {


    /**
     * 规则id
     */
    @ApiModelProperty(value = "规则id")
    @NotNull
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer ruleId;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    @DynamicQuery(DynamicQuery.QType.eq)
    @NotNull
    private Integer departmentId;

    /**
     * 部门任务
     */
    @ApiModelProperty(value = "部门任务")
    private String taskDetail;

    @ApiModelProperty(value = "规则体系")
    @NotNull
    private Integer rootRuleId;
}
