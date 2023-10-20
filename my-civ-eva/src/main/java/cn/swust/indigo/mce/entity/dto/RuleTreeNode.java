package cn.swust.indigo.mce.entity.dto;

import cn.swust.indigo.admin.entity.dto.TreeNode;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RuleTreeNode extends TreeNode<RuleTreeNode> implements Serializable {
    public RuleTreeNode(){
        children = new ArrayList<RuleTreeNode>();
    }

    private static final long serialVersionUID = 1L;
    /**
     * 规则id
     */
    @ApiModelProperty(value = "规则id")
    private Integer ruleId;

    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称 长度: 1-100")
    private String ruleName;

//    /**
//     * 类型 0网上申报，1 实地考察 2 问卷调查
//     */
//    @ApiModelProperty(value = "类型 0网上申报，1 实地考察 2 问卷调查")
//    private Integer type;

    /**
     * 规则名拼音
     */
    @ApiModelProperty(value = "规则名拼音 长度: 1-100")
    private String rulePy;

    /**
     * 规则简介
     */
    @ApiModelProperty(value = "规则简介 长度: 1-200")
    private String ruleBrief;

    /**
     * 规则要求
     */
    @ApiModelProperty(value = "规则要求 长度: 1-400")
    private String description;

    /**
     * 是否提交材料 0未提交 1已提交
     */
    @ApiModelProperty(value = "是否提交材料 0未提交 1已提交")
    private Boolean submit;

    /**
     * 满分
     */
    @ApiModelProperty(value = "满分")
    private Double fullMarks;

    /**
     * 一票否决 0不是一票否决 1，一票否决
     */
    @ApiModelProperty(value = "一票否决 0不是一票否决 1，一票否决 长度: 1-1")
    private Integer veto;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;


    /**
     * 规则体系根ID
     */
    @ApiModelProperty(value = "规则体系根Id")
    private Integer rootRuleId;
    /**
     * 规则等级
     */
    @ApiModelProperty("规则等级")
    private Integer level;
    /**
     * 判断是否选中，false为没有选中，true为选中
     */
    @ApiModelProperty(value = "判断是否选中")
    private boolean isChecked=false;
}

