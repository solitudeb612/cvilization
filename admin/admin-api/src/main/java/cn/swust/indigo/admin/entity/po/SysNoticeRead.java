package cn.swust.indigo.admin.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 系统通知阅读
 *
 * @author lhz
 * @date 2023-03-01 13:39:24
 */
@Data
@TableName("sys_notice_read")
@AllArgsConstructor

@ApiModel(value = "系统通知阅读")
public class SysNoticeRead {
    private static final long serialVersionUID = 1L;

    /**
     *通知id
     */

    @NotNull
    @TableId
    @ApiModelProperty(value = "")
    private Integer noticeId;


    /**
     * 用户id
     */
    @NotNull
    @Size(min = 1, max = 32, message = "用户id 最大为32")
    @ApiModelProperty(value = "用户id 长度: 1-32")
    private Integer userId;


    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

}
