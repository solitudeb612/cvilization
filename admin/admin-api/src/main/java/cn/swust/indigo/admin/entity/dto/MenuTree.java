package cn.swust.indigo.admin.entity.dto;

import cn.swust.indigo.admin.entity.vo.MenuVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单树
 *
 * @author lengleng
 * @date 2017年11月9日23:33:27
 */
@Data
@ApiModel(value = "菜单树")

public class MenuTree extends TreeNode {
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;
    private boolean spread = false;
    /**
     * 前端路由标识路径
     */
    @ApiModelProperty(value = "前端路由标识路径")
    private String path;
    /**
     * 路由缓冲
     */
    @ApiModelProperty(value = "路由缓冲")
    private String keepAlive;
    /**
     * 权限编码
     */
    @ApiModelProperty(value = "权限编码")
    private String code;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @ApiModelProperty(value = "菜单类型,0:菜单 1:按钮")
    private String type;
    /**
     * 菜单标签
     */
    @ApiModelProperty(value = "菜单标签")
    private String label;
    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值")
    private Integer sort;

    /**
     * 前端url地址
     */

    @ApiModelProperty(value = "前端url地址")
    private String url;

    /**
     * 前端component
     */
    @ApiModelProperty(value = "前端Component")
    private String component;


    /**
     * 隐藏字段id
     */
    @ApiModelProperty(value = "是否隐藏菜单(0:隐藏， 1:显示)")
    private String hide;


    public MenuTree() {
    }

    public MenuTree(int id, String name, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.label = name;
    }

    public MenuTree(int id, String name, MenuTree parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.name = name;
        this.label = name;
    }

    public MenuTree(MenuVO menuVo) {
        this.id = menuVo.getMenuId();
        this.parentId = menuVo.getParentId();
        this.icon = menuVo.getIcon();
        this.name = menuVo.getName();
        this.path = menuVo.getPath();
        this.type = menuVo.getType();
        this.label = menuVo.getName();
        this.sort = menuVo.getSort();
        this.keepAlive = menuVo.getKeepAlive();
    }
}
