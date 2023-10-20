package cn.swust.indigo.mce.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "")
public class QSite {

    /**
     * 地址
     */
    @Size(max = 255, message = "地址 最大为255")
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private Integer leaderId;

}
