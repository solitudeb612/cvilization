package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = " ")
public class QTaskCommit {

    /**
     * 任务id
     */
    @NotNull
    @ApiModelProperty(value="任务id")
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer taskId;
}
