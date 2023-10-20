package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QCommitReview {

    /**
     * 申报报告id
     */
    @ApiModelProperty(value="申报报告id")
    private Integer reportId;

    /**
     * 分数
     */
    @ApiModelProperty(value="分数")
    private Integer grade;

    /**
     * 部门id
     */
    @ApiModelProperty(value="评审部门id")
    private Integer reviewDeptId;

    /**
     * 评审意见详情
     */
    @Size(min = 1, max = 255, message = "评审意见详情 最大为255")
    @ApiModelProperty(value = "评审意见详情 长度: 1-255")
    private String reviewInfo;

    /**
     * 是否驳回
     */
    @ApiModelProperty(value="是否驳回")
    private boolean isRejected = false;

}
