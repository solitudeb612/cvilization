package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Generated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 新闻
 *
 * @author lhz
 * @date 2023-02-24 18:02:42
 */
@Data
@TableName("sys_news")

@ApiModel(value = "新闻")
public class News {
    private static final long serialVersionUID = 1L;

    /**
     * 新闻id
     */
    @TableId(value = "news_id", type = IdType.AUTO)
    @NotNull
    @ApiModelProperty(value = "新闻id")
    private Integer newsId;


    /**
     * 新闻名称
     */
    @Size(min = 1, max = 100, message = "新闻名称 最大为100")
    @ApiModelProperty(value = "新闻名称 长度: 1-100")
    private String newsName;


    /**
     * 状态：0草稿，1发布
     */
    @NotNull
    @ApiModelProperty(value = "状态：0草稿，1发布")
    private Integer status = 0;


    /**
     * 新闻类型：0通知公告，1为改革动态，2操作指南
     */
    @NotNull
    @ApiModelProperty(value = "新闻类型：0通知公告，1为改革动态，2操作指南")
    private Integer newsType;


    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    /**
     * 创建人
     */
    @NotNull
    @Size(min = 1, max = 32, message = "创建人 最大为32")
    @ApiModelProperty(value = "创建人 长度: 1-32")
    private String creatorId;


    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Integer approveNum = 0;


    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    /**
     * 阅读次数
     */
    @ApiModelProperty(value = "阅读次数")
    private Integer readNum = 0;


    /**
     * 更新时间
     */
    @NotNull
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Integer updaterId;

    /**
     * 新闻正文
     */
    @TableField(exist = false)
    @ApiModelProperty(value="新闻正文 长度: 1-4294967295")
    private String context;

    /**
     * 是否点赞 0未点赞 1点赞
     */
    @TableField(exist = false)
    @ApiModelProperty(value="是否点赞 0未点赞 1点赞")
    private Boolean praise = false;

}
