package cn.swust.indigo.admin.entity.vo;

import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @program:
 * @description:
 **/
@Data
@ApiModel(value = "字典项目分页查询条件")
public class QSysDictItem {
    private static final long serialVersionUID = 1L;

    /**
     * 所属字典类id
     */
    @ApiModelProperty(value = "所属字典类id")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    @NotNull
    private Integer dictId;

    /**
     * 数据值
     */
    @ApiModelProperty(value = "数据值")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String value;

    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String label;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    @DynamicQuery(value = DynamicQuery.QType.eq)
    private String type;

}
