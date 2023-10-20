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
public class QGuide {

    /**
     * 指南名字
     */
    @ApiModelProperty(value = "指南名字 长度: 1-100")
    @DynamicQuery(DynamicQuery.QType.like)
    private String guideName;

    /**
     * 指南状态 0 初稿 1 已审核 2 发布 3 开始申报 4 申报结束  5 指南结束
     */
    @DynamicQuery(DynamicQuery.QType.eq)
    private Integer status;

    /**
     *创建年
     */
    private Integer year;
}
