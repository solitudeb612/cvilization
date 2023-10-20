package cn.swust.indigo.admin.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形节点
 */

@Data
@ApiModel(value = "树形节点")

public class TreeNode<T extends TreeNode> implements Serializable {
    private static final long serialVersionUID = 1L;

    /***
     * 当前节点id
     */
    @ApiModelProperty(value = "当前节点id")
    protected int id;
    /***
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    protected int parentId;
    /***
     * 子节点列表
     */
    @ApiModelProperty(value = "子节点列表")
    protected List<T> children = new ArrayList<>();

    public void add(T node) {
        children.add(node);
    }
}
