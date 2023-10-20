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
@ApiModel(value = "自查报告查询条件")
public class QCheckReport {


    /**
     * 实地考察活动id
     */
    @NotNull
    @ApiModelProperty(value = "实地考察活动id")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer checkId;

    /**
     * 区域部门ID
     */
    @ApiModelProperty(value = "区域部门ID")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer departmentId;

    @ApiModelProperty(value = "检查组id")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer checkGroupId;

    /**
     * 主体责任单位ID
     */
    @ApiModelProperty(value = "主体责任单位ID")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer mainDepartmentId;

    /**
     * 指导责任单位ID
     */
    @ApiModelProperty(value = "指导责任单位ID")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer guidanceDepartmentId;

    /**
     * 提交者ID
     */
    @ApiModelProperty(value = "提交者ID")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer creatorId;

    /**
     * 提交地点
     */
    @ApiModelProperty(value = "提交地点")
    @DynamicQuery(DynamicQuery.QType.like)
    private String address;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    @DynamicQuery(DynamicQuery.QType.between)
    private LocalDateTime commitTime;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private LocalDateTime commitTimeEnd;
}
