package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 申报内容详情 主要是图片 
 * </p>
 *
 * @author binaryCat
 * @since 2023-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("commit_img_star")
@ApiModel(value="CommitImgStar对象", description="申报内容详情 主要是图片 ")
@NoArgsConstructor
@AllArgsConstructor
public class CommitImgStar implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "对应的reportId")
    private Integer reportId;

    @ApiModelProperty(value = "对应的commitDetailId")
    @TableField(value = "commit_detail_id")
    private Integer commitDetailId;

    @ApiModelProperty(value = "指南id")
    @TableField("guide_id")
    private Integer guideId;

    @ApiModelProperty(value = "文件id")
    @TableField("file_id")
    private Integer fileId;

    @ApiModelProperty(value = "规则id")
    @TableField("rule_id")
    private Integer ruleId;

    @ApiModelProperty(value = "是否星标 默认 0 不是，1 是")
    @TableField("star")
    private Integer star;


}
