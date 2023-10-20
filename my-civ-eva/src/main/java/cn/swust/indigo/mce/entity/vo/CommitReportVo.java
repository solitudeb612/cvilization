package cn.swust.indigo.mce.entity.vo;


import cn.swust.indigo.mce.entity.po.CommitReport;
import cn.swust.indigo.mce.entity.po.CommitSpotCheck;
import cn.swust.indigo.mce.entity.po.TaskCommitDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitReportVo extends CommitReport {
    private String taskDetail;
    private String departmentName;
    private String creatorName;
    private String creatorPhone;
    private String ruleName;
    private List<TaskCommitDetail> taskCommitDetails;
    private CommitSpotCheck commitSpotCheck;

    private Integer reviewGrade;

    private String reviewOpinion;
}
