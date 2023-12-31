package cn.swust.indigo.mce.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitRuleTableInfoVo extends CommitRuleTableInfo {
    private Integer Score;

    private String departmentName;

    private int guideId;
}
