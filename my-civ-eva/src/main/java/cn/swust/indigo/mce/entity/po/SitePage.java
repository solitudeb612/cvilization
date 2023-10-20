package cn.swust.indigo.mce.entity.po;

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
 * 站点主页
 *
 * @author lhz
 * @date 2023-02-24 17:02:13
 */
@Data
@TableName("site_page")
@ApiModel(value = "站点主页")
public class SitePage {
    private static final long serialVersionUID = 1L;

    /**
     * 点位id
     */
    @TableId
    @NotNull
    @ApiModelProperty(value = "点位id")
    private Integer siteId;

    /**
     * 主页信息
     */
    @Size(min = 1, max = 65535, message = " 最大为65535")
    @ApiModelProperty(value = " 长度: 1-65535")
    private String context;

}
