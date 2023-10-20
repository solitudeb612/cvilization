package cn.swust.indigo.mce.entity.dto;

import cn.swust.indigo.admin.entity.dto.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RuleTreeCheckNode extends RuleTreeNode implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 判断是否选中，false为没有选中，true为选中
     */
    private boolean isChecked=false;
}

