package cn.swust.indigo.mce.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@TableName("check_group_criteria")

@ApiModel(value = "自查组部门规则(新）")
@AllArgsConstructor
@NoArgsConstructor
public class CheckGroupCriteria {

    @TableId(value = "group_criteria_id", type = IdType.AUTO)
    //标准ID
    private Integer group_criteria_id;

    // 分组ID
    private Integer group_id;

    // 标准名称
    private String criteria_name;

    // 考核方式
    private String method;


    //标准内容

    @TableField(exist = false)
    private List<CheckCriteriaContent> criteriaContentlist;

    @TableField(exist = false)
    //规则详情
    private List<CheckRuleDetail> checkRuleDetailList;

    //测评项目（大部分标准中不含这一项）
    private String criteria_item;

    //测评内容 （大部分标准中不含这一项）
    private String criteria_content;

    public Integer getGroup_criteria_id() {
        return group_criteria_id;
    }

    public void setGroup_criteria_id(Integer group_criteria_id) {
        this.group_criteria_id = group_criteria_id;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public String getCriteria_name() {
        return criteria_name;
    }

    public void setCriteria_name(String criteria_name) {
        this.criteria_name = criteria_name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<CheckRuleDetail> getCheckRuleDetailList() {
        return checkRuleDetailList;
    }

    public List<CheckCriteriaContent> getCriteriaContentlist() {
        return criteriaContentlist;
    }

    public void setCriteriaContentlist(List<CheckCriteriaContent> criteriaContentlist) {
        this.criteriaContentlist = criteriaContentlist;
    }



    public void setCheckRuleDetailList(List<CheckRuleDetail> checkRuleDetailList) {
        this.checkRuleDetailList = checkRuleDetailList;
    }
}
