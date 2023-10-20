package cn.swust.indigo.mce.entity.po;

import cn.swust.indigo.mce.entity.vo.SiteVo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Binary cat
 * @date 2023/4/7 15:38
 */
@Data
@ToString
public class ContextSite extends Site {
    @TableField("context")
    private String context;

    @TableField("nickname")
    private String leaderName;

    @TableField("department_name")
    private String departmentName;

    @ApiModelProperty(value = "站点缩略图url")
    @TableField(exist = false)
    private String picUrl;
}
