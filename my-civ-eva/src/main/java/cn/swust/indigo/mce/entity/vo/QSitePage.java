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
@ApiModel(value = " ")
public class QSitePage {

/**
    * 点位id
    */
    @ApiModelProperty(value="点位id")
    private Integer siteId;



    }
