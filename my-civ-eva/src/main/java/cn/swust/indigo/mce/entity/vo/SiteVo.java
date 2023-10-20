package cn.swust.indigo.mce.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import cn.swust.indigo.mce.entity.po.Site;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Binary cat
 * @date 2023/3/20 13:26
 */
@Data
@ApiModel(value = "站点详情视图")
public class SiteVo extends Site{
    @ApiModelProperty(value = "站点领导人姓名")
    @TableField("leaderName")
    private String leaderName;

    @ApiModelProperty(value = "领导人电话号")
    private String phoneNumber;

    @ApiModelProperty(value = "领导人手机电话号")
    private String telephoneNumber;

    @ApiModelProperty(value = "站点所属部门名")
    private String departmentName;

    @ApiModelProperty(value = "站点缩略图url")
    @TableField(exist = false)
    private String picUrl;
}
