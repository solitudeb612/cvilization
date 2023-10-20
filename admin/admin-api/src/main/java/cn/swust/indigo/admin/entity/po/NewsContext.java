package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 新闻详情
 *
 * @author lhz
 * @date 2023-02-24 18:02:57
 */
@Data
@TableName("sys_news_context")
@ApiModel(value = "新闻详情")
public class NewsContext {
private static final long serialVersionUID = 1L;
    
    /**
     * 新闻id
     */
    @TableId
        @NotNull
        @ApiModelProperty(value="新闻id")
    private Integer newsId;

    
    /**
     * 新闻正文
     */
    @ApiModelProperty(value="新闻正文 长度: 1-4294967295")
    private String context;
    

}
