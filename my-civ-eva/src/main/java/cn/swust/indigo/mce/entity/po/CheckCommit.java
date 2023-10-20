package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 自查报告提交内容
 *
 * @author lhz
 * @date 2023-03-18 10:33:42
 */
@Data
@TableName("check_commit")

@ApiModel(value = "自查报告提交内容")
public class CheckCommit {
    private static final long serialVersionUID = 1L;

    /**
     * 项目提交名称
     */
    @TableId(value = "check_commit_id", type = IdType.AUTO)
    @NotNull
    @ApiModelProperty(value = "项目提交名称")
    private Integer checkCommitId;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private Integer checkReportId;

    /**
     * 指南提交id
     */
    @ApiModelProperty(value = "自查规则id")
    private Integer checkRuleId;

    /**
     * 材料名称
     */
    @Size(min = 1, max = 255, message = "材料名称 最大为255")
    @ApiModelProperty(value = "材料名称 长度: 1-255")
    private String name;

    /**
     * 图片文件id
     */
    @Size(min = 1, max = 255, message = "图片文件id 最大为255")
    @ApiModelProperty(value = "图片文件id 长度: 1-255")
    private String fileIds;

    /**
     * 文本材料
     */
    @Size(min = 1, max = 255, message = "文本材料 最大为255")
    @ApiModelProperty(value = "文本材料 长度: 1-255")
    private String context;

    /**
     * 1.图片，2，文本，3附件
     */
    @ApiModelProperty(value = "1.图片，2，文本，3附件")
    private Integer type;

    /**
     * 是否通过：1.通过 0.不通过
     */
    @ApiModelProperty(value = "是否通过：1.通过 0.不通过")
    private Integer isPassed;

    /**
     * 提交地点
     */
    @Size(min = 1, max = 100, message = "提交地点 最大为100")
    @ApiModelProperty(value = "提交地点 长度: 1-100")
    private String address;

    /**
     * 提交设备
     */
    @Size(min = 1, max = 100, message = "提交设备 最大为100")
    @ApiModelProperty(value = "提交设备 长度: 1-100")
    private String commitDevice;

    /**
     * 提交人
     */
    @ApiModelProperty(value = "提交人")
    private Integer creatorId;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private LocalDateTime commitTime;

}
