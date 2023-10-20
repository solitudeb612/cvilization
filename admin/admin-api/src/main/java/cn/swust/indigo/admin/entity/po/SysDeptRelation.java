package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 部门关系表
 * </p>
 */
@Data
@ApiModel(value = "部门关系")
public class SysDeptRelation extends Model<SysDeptRelation> {

    private static final long serialVersionUID = 1L;
    /**
     * 祖先节点
     */
    @NotNull
    @ApiModelProperty(value = "祖先节点")
    private Integer ancestor;
    /**
     * 后代节点
     */
    @NotNull
    @ApiModelProperty(value = "后代节点")
    private Integer descendant;


}
