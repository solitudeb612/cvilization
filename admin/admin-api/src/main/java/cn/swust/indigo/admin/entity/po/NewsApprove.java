package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 新闻点赞数据
 *
 * @author lhz
 * @date 2023-02-24 17:00:04
 */
@Data
@TableName("sys_news_approve")

@ApiModel(value = "新闻点赞数据")
public class NewsApprove {
private static final long serialVersionUID = 1L;
    
    /**
     * 新闻id
     */
    @TableId
    @NotNull
    @ApiModelProperty(value="新闻id")
    private Integer newsId;
    

    
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id 长度: 1-32")
    private Integer userId;
    

    
    /**
     * 是否点赞 0未点赞 1点赞
     */
        @NotNull
        @ApiModelProperty(value="是否点赞 0未点赞 1点赞")
    private Boolean praise;
    

    

    
}
