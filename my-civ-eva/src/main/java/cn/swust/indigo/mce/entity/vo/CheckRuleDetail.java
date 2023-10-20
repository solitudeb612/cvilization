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
@TableName("check_group_rule")

@ApiModel(value = "自查组部门规则(新）")
@AllArgsConstructor
@NoArgsConstructor
public class CheckRuleDetail {


    //规则ID
    private Integer group_rule_id;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    //问题序号（测评明细的序号）
    private Integer sort;

    //标准笔数
    private Integer number;

    //单笔分值
    private double score;

    //测评明细
    private String rule_detail;

    //权重
    private String weight;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getRule_detail() {
        return rule_detail;
    }

    public void setRule_detail(String rule_detail) {
        this.rule_detail = rule_detail;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getGroup_rule_id() {
        return group_rule_id;
    }

    public void setGroup_rule_id(Integer group_rule_id) {
        this.group_rule_id = group_rule_id;
    }
}
