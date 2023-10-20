package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 自查报告
 *
 * @author lhz
 * @date 2023-03-18 10:24:27
 */
@Data
@TableName(value="check_report" ,resultMap = "checkReportMap")
@ApiModel(value = "自查报告")
public class CheckReport {
    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @TableId(value = "check_report_id",type = IdType.AUTO)
    @NotNull
    @ApiModelProperty(value = "检查报告ID")
    private Integer checkReportId;

    @ApiModelProperty(value = "检查组id")
    private Integer checkGroupId;

    @TableField(exist = false)
    private String checkGroupName;

    /**
     * 考察活动id
     */
    @NotNull
    @ApiModelProperty(value = "考察活动id")
    private Integer checkId;

    /**
     * 区域部门ID
     */
    @ApiModelProperty(value = "区域部门ID")
    private Integer departmentId;

    /**
     * 主体责任单位ID
     */
    @ApiModelProperty(value = "主体责任单位ID")
    private Integer mainDepartmentId;

    /**
     * 指导责任单位ID
     */
    @ApiModelProperty(value = "指导责任单位ID")
    private Integer guidanceDepartmentId;

    /**
     * 区域部门名
     */
    @ApiModelProperty(value = "区域部门名")
    @TableField(exist = false)
    private String departmentName;

    /**
     * 主体责任单位ID
     */
    @ApiModelProperty(value = "主体责任单位名")
    @TableField(exist = false)
    private String mainDepartmentName;

    /**
     * 指导责任单位名
     */
    @ApiModelProperty(value = "指导责任单位名")
    @TableField(exist = false)
    private String guidanceDepartmentName;

    /**
     * 提交地点
     */
    @ApiModelProperty(value = "提交地点")
    private String address;

    /**
     * 项目名称
     */
    @Size(min = 1, max = 255, message = "项目名称 最大为255")
    @ApiModelProperty(value = "项目名称 长度: 1-255")
    private String reportName;

    /**
     * 项目状态：0初稿，1提交
     */
    @NotNull
    @ApiModelProperty(value = "项目状态：0初稿，1提交")
    private Integer status=0;

    /**
     * 备注
     */
    @Size(min = 1, max = 400, message = "备注 最大为400")
    @ApiModelProperty(value = "备注 长度: 1-400")
    private String memo;

    /**
     * 创建者id
     */
    @NotNull
    @ApiModelProperty(value = "创建者id")
    private Integer creatorId;

    /**
     * 修改者id
     */
    @ApiModelProperty(value = "修改者id")
    private Integer updaterId;

    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private LocalDateTime commitTime;

    /**
     * 提交设备
     */
    @ApiModelProperty(value = "提交设备")
    private String commitDevice;

    /**
     * 通过条数
     */
    @ApiModelProperty(value = "通过条数")
    private Integer passedCount;

    /**
     * 失败条数
     */
    @ApiModelProperty(value = "失败条数")
    private Integer failCount;


}
