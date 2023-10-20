package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.mce.entity.po.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Binary cat
 * @date 2023/3/25 15:58
 */
@Data
public class CommitReportAndDetail extends CommitReport {

    /**
     * 自己根据规则需要提交的每条内容
     */
    @ApiModelProperty(value = "自己根据规则需要提交的每条内容")
    List<CommitDetail> commitDetailList;

    @ApiModelProperty(value = "抽查列表")
    List<CommitSpotCheck> spotChecksList;

    @ApiModelProperty(value = "星标图片ID")
    List<CommitImgStar> commitImgStarList;
}
