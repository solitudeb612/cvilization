package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program:
 * @description:
 **/
@Data
@ApiModel(value = "字典分页查询条件")
public class QSysDict {
    /**
     * 编号
     */
    @TableId
    @ApiModelProperty(value = "字典编号")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer id;
    /**
     * 类型
     */
    @ApiModelProperty(value = "字典类型")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String type;
    /**
     * 描述
     */
    @ApiModelProperty(value = "字典描述")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String description;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @DynamicQuery(value = DynamicQuery.QType.between)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @DynamicQuery(value = DynamicQuery.QType.between)
    private LocalDateTime updateTime;
    /**
     * 是否是系统内置
     */
    @TableField(value = "`system`")
    @ApiModelProperty(value = "是否系统内置")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private String system;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String remarks;
    /**
     * 删除标记
     */
    @TableLogic
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private String delFlag;
}
