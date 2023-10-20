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
 * 指南
 *
 * @author lhz
 * @date 2023-02-24 17:26:51
 */
@Data
@TableName("guide")
@ApiModel(value = "指南")
public class Guide {
    private static final long serialVersionUID = 1L;

    /**
     * 指南id
     */
    @TableId(value = "guide_id",type = IdType.AUTO)
    @NotNull
    @ApiModelProperty(value = "指南id")
    private Integer guideId;


    /**
     * 指南名字
     */
    @NotNull
    @Size(min = 1, max = 100, message = "指南名字 最大为100")
    @ApiModelProperty(value = "指南名字 长度: 1-100")
    private String guideName;





    /**
     * 指南简称
     */
    @Size(min = 1, max = 400, message = "指南简称 最大为400")
    @ApiModelProperty(value = "指南简称 长度: 1-400")
    private String guideBrief;


    /**
     * 建立人
     */
    @NotNull
    @ApiModelProperty(value = "建立人 长度: 1-32")
    private Integer creatorId;


    /**
     * 活动的开始时间
     */
    @ApiModelProperty(value = "活动的开始时间")
    private LocalDateTime beginTime;

    /**
     * 上传材料附件ids
     */
    @Size(min = 1, max = 400, message = "上传材料附件ids 最大为400")
    @ApiModelProperty(value = "上传材料附件ids 长度: 1-400")
    private String materialIds;


    /**
     * 活动的结束时间
     */
    @ApiModelProperty(value = "活动的结束时间")
    private LocalDateTime endTime;

    /**
     * 备注
     */
    @Size(min = 1, max = 400, message = "备注 最大为400")
    @ApiModelProperty(value = "备注 长度: 1-400")
    private String memo;


    /**
     * 指南状态 0 初稿 1 发布 2 开始申报 3 申报结束 4 指南结束
     */
    @NotNull
    @ApiModelProperty(value = "指南状态 0 初稿 1 发布 2 开始申报 3 申报结束 4 指南结束")
    private Integer status = 0;

    /**
     * 建立时间
     */
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
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    /**
     * 规则根id
     */
    @NotNull
    @ApiModelProperty(value = "规则根id")
    private Integer rootRuleId;

}
