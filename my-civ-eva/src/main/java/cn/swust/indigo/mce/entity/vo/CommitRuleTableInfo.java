package cn.swust.indigo.mce.entity.vo;


import cn.swust.indigo.mce.entity.po.TaskCommit;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;


@Data
@ApiModel(description = "网上申报项目查看返回的返回条目-条目")
public class CommitRuleTableInfo {
    private Integer ruleId;
    private Integer taskId;
    private String taskDetail;
    private String ruleLevel1Name;
    private String ruleLevel2Name;
    private String ruleLevel3Name;
    private String ruleLevel4Name;
    private String departmentName;
    private Integer departmentId;
    private Boolean leadFlag;
    @JSONField(serialize = false)
    private Date lastCommitTime;

    @JSONField(serialize = false)
    private Integer commitCount = 0;

    private Date CommitTime;

    /**
     * 状态： 0：未申报 1：表示草稿 2：表示已经提交
     */
    @ApiModelProperty(value = "0：未申报 1：表示草稿 2：表示已经提交",name = "状态")
    private Integer status;

    @ApiModelProperty(value = "0.草稿 1.提交 2.通过 3.牵头部门驳回 4.抽查驳回")
    private Integer reportStatus;

    /**
     * 网上申报报告 ID
     */
    @ApiModelProperty(value = "网上申报报告 ID",name = "网上申报报告 ID")
    private Integer reportId;
}
