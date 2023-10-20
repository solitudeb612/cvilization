package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.mce.entity.po.CheckGroup;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "检查组")
public class QCheckGroup extends CheckGroup {

    /**
     *检查组id
     */
    @TableId(value = "check_group_id", type = IdType.AUTO)
    @ApiModelProperty(value = "规则组id")
    private Integer checkGroupId;
    /**
     * 说明
     */
    @TableField(value = "memo")
    @ApiModelProperty(value = "规则组说明")
    private String memo;
    /**
     * 组名
     */
    @ApiModelProperty(value = "规则组名称")
    @TableField(value = "group_name")
    private String groupName;
}
