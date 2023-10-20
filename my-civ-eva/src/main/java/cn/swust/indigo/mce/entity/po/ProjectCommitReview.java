package cn.swust.indigo.mce.entity.po;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class ProjectCommitReview {

    /**
     * 项目提交项ID
     */
    @NotNull
    @ApiModelProperty(value = "项目提交项ID")
    private Integer projectCommitId;


    /**
     * 评审人id
     */
    @ApiModelProperty(value = "评审人id")
    private Integer reviewerId;
    /**
     * 评审人id
     */
    @ApiModelProperty(value = "评审时间")
    private Date reviewTime;
    /**
     * 评审意见
     */
    @ApiModelProperty(value = "评审意见")
    private String reviewOpinion;

    /**
     * 成绩
     */
    @ApiModelProperty(value = "成绩")
    private Float reviewScore;
    /**
     * 成绩
     */
    @ApiModelProperty(value = "等级")
    private Integer reviewGrade;


}
