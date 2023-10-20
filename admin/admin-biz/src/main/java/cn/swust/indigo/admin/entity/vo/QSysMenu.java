package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author lengleng
 * @since 2017-11-08
 */
@Data
@ApiModel(value = "菜单")

public class QSysMenu extends Model<QSysMenu> {

    private static final long serialVersionUID = 1L;
    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    @ApiModelProperty(value = "菜单id")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer menuId;
    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称")
    @Size(min = 1, max = 32, message = "菜单名称长度1-32")
    @DynamicQuery(value = DynamicQuery.QType.like)
    @NotNull
    private String name;
    /**
     * 菜单权限标识
     */
    @ApiModelProperty(value = "菜单权限标识")
    @Size(min = 0, max = 32, message = "菜单权限标识0-32")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String permission;
    /**
     * 父菜单ID
     */
    @NotNull(message = "菜单父ID不能为空")
    @ApiModelProperty(value = "菜单父id")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer parentId;
    /**
     * 图标
     */
    @ApiModelProperty(value = "菜单图标")
    @Size(min = 0, max = 32, message = "菜单图标标识0-32")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String icon;
    /**
     * 前端路由标识路径，默认和 comment 保持一致
     * 过期
     */
    @ApiModelProperty(value = "前端路由标识路径")
    @Size(min = 0, max = 128, message = "菜前端路由标识路径字符长度0-128")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String path;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private LocalDateTime updateTime;

    /**
     * 前端url地址
     */

    @ApiModelProperty(value = "前端url地址")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String url;

    /**
     * 前端component
     */
    @ApiModelProperty(value = "前端Component")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String component;
}
