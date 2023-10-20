package cn.swust.indigo.mce.entity.po;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotNull;

/**
 * 项目抽查(CommitSpotCheck)表实体类
 *
 * @author makejava
 * @since 2023-03-25 19:45:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("commit_spot_check")
public class CommitSpotCheck  {
    @TableId(type = IdType.AUTO,value = "commit_spot_id")
    @ApiModelProperty(value = "项目抽查ID")
    private Integer commitSpotId;

    @ApiModelProperty("报告ID")
    private Integer reportId;

    @ApiModelProperty("等级")
    private Integer reviewGrade;

    @ApiModelProperty("意见")
    private String reviewOpinion;

    @ApiModelProperty("评审人id")
    private Integer reviewerId;

    @ApiModelProperty("评审时间")
    private Date reviewTime;

    @ApiModelProperty("抽查部门")
    private Integer departmentId;
}
