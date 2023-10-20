package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
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
@ApiModel(value = "自查报告查询")
public class QCheckCommit {

    /**
     * 规则Id
     */
    @ApiModelProperty(value="规则id")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer ruleId;

    /**
    * 部门Id
    */
    @ApiModelProperty(value="部门id")
    @DynamicQuery(DynamicQuery.QType.eq)
    @NotNull
    private Integer departmentId;
    /**
     * 站点Id
     */
    @ApiModelProperty(value="siteid")
    @DynamicQuery(DynamicQuery.QType.eq)
    @NotNull
    private Integer siteId;
    /**
    * 材料名称
    */
    @Size(max = 255, message = "材料名称 最大为255")
    @ApiModelProperty(value="材料名称")
    @DynamicQuery(DynamicQuery.QType.like)
    private String name;


    }
