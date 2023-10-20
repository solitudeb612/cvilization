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
 * 项目详情
 *
 * @author lhz
 * @date 2023-02-24 18:03:36
 */
@Data
@TableName("project_detail")
@ApiModel(value = "项目详情")
public class ProjectDetail {
    private static final long serialVersionUID = 1L;

    /**
     * 项目详情ID 设置主键自动增长策略为自动策略
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "项目详情ID")
    private Integer projectId;


    /**
     * 项目详情 文本内容
     */
    @ApiModelProperty(value = "项目详情 文本内容 长度: 1-4294967295")
    @TableField("context")
    private String context;


}
