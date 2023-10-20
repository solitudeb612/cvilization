package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典表
 *
 * @author lengleng
 * @date 2019/03/19
 */
@Data
@ApiModel(value = "字典类型")
@TableName("sys_dict")
public class SysDict extends Model<SysDict> {
    private static final long serialVersionUID = 2020202L;

    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "字典编号")
    private Integer dictId;
    /**
     * 类型
     */
    @TableField("type")
    @ApiModelProperty(value = "字典类型")
    private String type;
    /**
     * 描述
     */
    @TableField("description")
    @ApiModelProperty(value = "字典描述")
    private String description;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 是否是系统内置
     */

    @TableField("system")
    @ApiModelProperty(value = "是否系统内置")
    private String system;
    /**
     * 备注信息
     */
    @TableField("remarks")
    @ApiModelProperty(value = "备注信息")
    private String remarks;
    /**
     * 删除标记
     */
    @TableLogic
    @TableField("del_flag")
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;


}
