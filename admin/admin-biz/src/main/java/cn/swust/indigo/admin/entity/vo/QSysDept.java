package cn.swust.indigo.admin.entity.vo;

import cn.hutool.db.Page;
import cn.swust.indigo.common.data.mybatis.annotation.DynamicQuery;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * <p>
 * 部门管理
 * </p>
 */
@Data
@ApiModel(value = "Q部门")
public class QSysDept {


    /**
     * 部门名称
     */
    @Size(min = 1, max = 50, message = "部门名称 最大为50")
    @ApiModelProperty(value = "部门名称 长度: 1-50")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String name;

    @Size(min = 1, max = 50, message = "部门名拼音首字母串")
    @ApiModelProperty(value = "部门名拼音首字母串")
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String departmentPy;


    /**
     * 联系人名字
     */
    @NotNull
    @Size(min = 1, max = 64, message = "部门管理员名字 最大为64")
    @ApiModelProperty(value = "部门管理员名字 长度: 1-64")
    @TableField(exist = false)
    @DynamicQuery(value = DynamicQuery.QType.like)
    private String leaderName;

    @ApiModelProperty(value = "index")
    private long index;

    @NotNull
    @ApiModelProperty(value = "size")
    private long size;
}
