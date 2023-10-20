package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 批量在线申报提交要求
 *
 * @author lhz
 * @date 2023-03-23 10:37:51
 */
@Data

@ApiModel(value = "批量在线申报提交要求")
public class BatchSaveTaskCommit {
    private static final long serialVersionUID = 1L;
    /**
     * 根据节点ID
     */

    @ApiModelProperty(value = "根据节点ID")
    private Integer rootRuleId;


    /**
     * 提交内容名称
     */
    @Size(min = 1, max = 255, message = "提交内容名称 最大为255")
    @ApiModelProperty(value = "提交内容名称 长度: 1-255")
    private String commitName;

    /**
     * 类型：1图片，2文本，3，文件，4 富文本
     */
    @NotNull
    @ApiModelProperty(value = "类型：1图片，2文本，3，文件，4 富文本")
    private Integer type;

    /**
     * 说明
     */
    @NotNull
    @Size(min = 1, max = 255, message = "说明 最大为255")
    @ApiModelProperty(value = "说明 长度: 1-255")
    private String memo;

    /**
     * 是否必填
     */
    @NotNull
    @ApiModelProperty(value = "是否必填：0.不是必填 1.必填")
    private Integer isRequired;

//    @NotNull
    @ApiModelProperty(value = "文件提交数量")
    private Integer commitNumber;

}
