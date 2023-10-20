package cn.swust.indigo.mce.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 自查通知
 *
 * @author lhz
 * @date 2023-03-18 10:14:00
 */
@Data
@TableName("self_check")
@ApiModel(value = "自查通知")
public class SelfCheck {
    private static final long serialVersionUID = 1L;

    /**
     * 自查id
     */
    @TableId(value = "check_id",type = IdType.AUTO)
    @NotNull
    @ApiModelProperty(value = "自查id")
    private Integer checkId;

    /**
     * 自查名字
     */
    @NotNull
    @Size(min = 1, max = 100, message = "自查名字 最大为100")
    @ApiModelProperty(value = "自查名字 长度: 1-100")
    private String checkName;

    /**
     * 自查简称
     */
    @Size(min = 1, max = 400, message = "自查简称 最大为400")
    @ApiModelProperty(value = "自查简称 长度: 1-400")
    private String checkBrief;

    /**
     * 活动的开始时间
     */
    @ApiModelProperty(value = "活动的开始时间")
    private LocalDateTime beginTime;

    /**
     * 活动的结束时间
     */
    @ApiModelProperty(value = "活动的结束时间")
    private LocalDateTime endTime;

    /**
     * 活动状态 0 初稿 1 发布 2 开始申报 3 申报结束 4 活动结束
     */
    @NotNull
    @ApiModelProperty(value = "活动状态 0 活动初稿 1 活动发布 2 活动开始 3 活动结束")
    private Integer status = 0;

    /**
     * 备注
     */
    @Size(min = 1, max = 400, message = "备注 最大为400")
    @ApiModelProperty(value = "备注 长度: 1-400")
    private String memo;

    /**
     * 上传材料附件ids
     */
    @Size(min = 1, max = 100, message = "上传材料附件ids 最大为100")
    @ApiModelProperty(value = "上传材料附件ids 长度: 1-100")
    private String materialIds;

    /**
     * 建立人
     */
    @NotNull
    @ApiModelProperty(value = "建立人")
    private Integer creatorId;

    /**
     * 建立时间
     */
    @NotNull
    @ApiModelProperty(value = "建立时间")
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Integer updaterId;

    /**
     * 更新时间
     */
    @NotNull
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    /**
     * 提交数量
     */
    @ApiModelProperty(value = "提交数量")
    @TableField(exist = false)
    private Integer commitNum;

    /**
     * 未提交数量
     */
    @ApiModelProperty(value = "未提交数量")
    @TableField(exist = false)
    private Integer disCommitNum;


}
