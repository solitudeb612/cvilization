package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 字典项
 */
@Data
@ApiModel(value = "字典项")
@TableName("sys_dict_item")
public class SysDictItem extends Model<SysDictItem> {
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "字典项id")
    private Integer id;

    /**
     * 所属字典类id
     */
    @ApiModelProperty(value = "所属字典类id")
    private Integer dictId;
    /**
     * 数据值
     */
    @ApiModelProperty(value = "数据值")
    private String value;
    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名")
    private String label;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String type;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 排序（升序）
     */
    @ApiModelProperty(value = "排序值，默认升序")
    private Integer sort;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remarks;
    /**
     * 删除标记
     */
    @TableLogic
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;


}
