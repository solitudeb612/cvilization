package cn.swust.indigo.admin.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "通知信息查询条件")
public class QSysNotice {


    /**
     * 公告ID
     */
    @ApiModelProperty(value = "公告ID")
    private Integer noticeId;


    /**
     * 标题
     */
    @Size(max = 128, message = "标题 最大为128")
    @ApiModelProperty(value = "标题")

    private String title;


    /**
     * 公告类型（1通知 2公告）
     */

    @ApiModelProperty(value = "公告类型（1通知 2公告）")
    private Integer noticeType;


    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;


}
