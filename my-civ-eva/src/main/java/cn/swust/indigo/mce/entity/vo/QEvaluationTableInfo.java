package cn.swust.indigo.mce.entity.vo;

import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;

@Data
@Api(value = "简略查询规则指南")
public class QEvaluationTableInfo implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 第一级规则Id
     */
    private Integer ruleLevel1Id;
    /**
     * 第一级规则名
     */
    private String ruleLevel1Name;

    /**
     * 第二级规则Id
     */
    private Integer ruleLevel2Id;
    /**
     * 第二级规则名
     */
    private String ruleLevel2Name;

    /**
     * 第三级规则Id
     */
    private Integer ruleLevel3Id;
    /**
     * 第三级规则名
     */
    private String ruleLevel3Name;

    /**
     * 第四级规则Id
     */
    private Integer ruleLevel4Id;
    /**
     * 第四级规则名
     */
    private String ruleLevel4Name;
}
