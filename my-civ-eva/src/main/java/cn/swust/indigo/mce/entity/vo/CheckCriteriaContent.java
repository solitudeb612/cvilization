package cn.swust.indigo.mce.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("check_criteria_content")
public class CheckCriteriaContent {
    //标准内容ID
    @TableId(value = "criteria_content_id", type = IdType.AUTO)
    private int criteria_content_id;

    //标准ID
    private int group_criteria_id;

    //标准内容
    private String criteria_content;

    public int getCriteria_content_id() {
        return criteria_content_id;
    }

    public void setCriteria_content_id(int criteria_content_id) {
        this.criteria_content_id = criteria_content_id;
    }

    public int getGroup_criteria_id() {
        return group_criteria_id;
    }

    public void setGroup_criteria_id(int group_criteria_id) {
        this.group_criteria_id = group_criteria_id;
    }

    public String getCriteria_content() {
        return criteria_content;
    }

    public void setCriteria_content(String criteria_content) {
        this.criteria_content = criteria_content;
    }
}
