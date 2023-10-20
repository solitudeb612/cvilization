package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 */
@Data
@ApiModel(value = "菜单")

public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;
    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    @ApiModelProperty(value = "菜单id")
    private Integer menuId;
    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称")
    @Size(min = 1, max = 32, message = "菜单名称长度1-32")
    @NotNull
    private String name;
    /**
     * 菜单权限标识
     */
    @ApiModelProperty(value = "菜单权限标识")
    @Size(min = 0, max = 32, message = "菜单权限标识0-32")
    private String permission;
    /**
     * 父菜单ID
     */
    @NotNull(message = "菜单父ID不能为空")
    @ApiModelProperty(value = "菜单父id")
    private Integer parentId;
    /**
     * 图标
     */
    @ApiModelProperty(value = "菜单图标")
    @Size(min = 0, max = 32, message = "菜单图标标识0-32")
    @TableField()
    private String icon;
    /**
     * 前端路由标识路径，默认和 comment 保持一致
     * 过期
     */
    @ApiModelProperty(value = "前端路由标识路径")
    @Size(min = 0, max = 128, message = "菜前端路由标识路径字符长度0-128")
    private String path;
    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值")
    private Integer sort;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @NotNull(message = "菜单类型不能为空")
    @ApiModelProperty(value = "菜单类型,0:菜单 1:按钮")
    @Size(min = 0, max = 1, message = "菜单类型字符长度1")
    private String type;
    /**
     * 路由缓冲
     */
    @ApiModelProperty(value = "路由缓冲0-开启，1- 关闭")
    @Size(min = 0, max = 1, message = "路由缓冲字符长度1")
    private String keepAlive;
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
     * 0--正常 1--删除
     */
    @TableLogic
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    @Size(min = 0, max = 1, message = "删除标记字符长度1")
    private String delFlag;


}
