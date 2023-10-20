package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "新闻查询")
public class QNews {


    /**
     * 新闻名称
     */
    @ApiModelProperty(value = "新闻名称")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String newsName;



    /**
     * 新闻类型：0通知公告，1为改革动态，2操作指南
     */
    @ApiModelProperty(value = "新闻类型：0通知公告，1为改革动态，2操作指南")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer newsType;

    /**
     * 状态：0草稿，1发布
     */
    @NotNull
    @ApiModelProperty(value = "状态：0草稿，1发布")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private Integer status;


    /**
     * 创建人
     */
    @Size(max = 32, message = "创建人 最大为32")
    @ApiModelProperty(value = "创建人")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String creatorId;


//    /**
//     * 创建时间
//     */
//    @ApiModelProperty(value = "创建时间")
//    @DynamicQuery(value = DynamicQuery.QType.between)
//    private LocalDateTime createTime;

//    /**
//     * 创建时间结束
//     */
//    @ApiModelProperty(value = "创建时间结束 ")
//    private LocalDateTime createTimeEnd;
//
//    /**
//     * 更新时间
//     */
//    @ApiModelProperty(value = "更新时间")
//    @DynamicQuery(value = DynamicQuery.QType.between)
//    private LocalDateTime updateTime;
//
//    /**
//     * 更新时间结束
//     */
//    @ApiModelProperty(value = "更新时间结束 ")
//    private LocalDateTime updateTimeEnd;


}
