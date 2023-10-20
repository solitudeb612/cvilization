package cn.swust.indigo.mce.entity.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Binary cat
 * @date 2023/4/4 15:57
 */
@Data
public class StarImageFileCommitDetail extends CommitDetail {

    /**
     * 提交的申请中所有星标图片文件ID集合
     */
    @ApiModelProperty(value = "提交的申请中所有星标图片文件ID集合")
    List<Integer> starImageFileIds;
}
