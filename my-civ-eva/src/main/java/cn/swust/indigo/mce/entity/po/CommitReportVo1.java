package cn.swust.indigo.mce.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitReportVo1 {
    private Integer ruleId;
    private Integer departmentId;
    private Integer count;
    private Date commitTime;
}
