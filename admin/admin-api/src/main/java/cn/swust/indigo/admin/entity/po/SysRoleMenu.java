package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 角色菜单表
 * </p>
 */
@Data
@ApiModel(value = "角色菜单")

public class SysRoleMenu extends Model<SysRoleMenu> {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @NotNull
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
    /**
     * 菜单ID
     */
    @NotNull
    @ApiModelProperty(value = "菜单id")
    private Integer menuId;
}
